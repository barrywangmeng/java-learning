package cn.barrywangmeng.concurrent.threadpool;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 代表异步任务处理器<br>
 * 此处理器简单地封装了异步任务<br>
 * Spring 提供的还不是特别灵活方便，故使用此类型<br>
 * 
 * @author 王孛
 * @version 1.1
 */
public class AsyncProcessor {
	static final Logger LOGGER = LoggerFactory.getLogger(AsyncProcessor.class);

	/**
	 * 默认最大并发数<br>
	 * 等于当前处理器数 IO操作比较多，这里最大线程数为CPU*2
	 */
	private static final int DEFAULT_MAX_CONCURRENT = Runtime.getRuntime().availableProcessors() * 2;

	/**
	 * 线程池名称格式
	 */
	private static final String THREAD_POOL_NAME = "ExternalConvertProcessPool-%d";

	/**
	 * 线程工厂名称
	 */
	private static final ThreadFactory FACTORY = new BasicThreadFactory.Builder().namingPattern(THREAD_POOL_NAME)
			.daemon(true).build();

	/**
	 * 默认队列大小
	 */
	private static final int DEFAULT_SIZE = 500;

	/**
	 * 默认线程存活时间
	 */
	private static final long DEFAULT_KEEP_ALIVE = 60L;

	/**NewEntryServiceImpl.java:689
	 * Executor
	 */
	private static ExecutorService executor;

	/**
	 * 执行队列
	 */
	private static BlockingQueue<Runnable> executeQueue = new ArrayBlockingQueue<>(DEFAULT_SIZE);

	static {
		// 创建 Executor
		// 此处默认最大值改为处理器数量的 4 倍
		// 之前老代码采用硬编码，有 BUG
		try {
			executor = new ThreadPoolExecutor(DEFAULT_MAX_CONCURRENT, DEFAULT_MAX_CONCURRENT * 8, DEFAULT_KEEP_ALIVE,
					TimeUnit.SECONDS, executeQueue, FACTORY);

			// 关闭事件的挂钩
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

				@Override
				public void run() {
					AsyncProcessor.LOGGER.info("AsyncProcessor shutting down.");

					executor.shutdown();

					try {

						// 等待1秒执行关闭
						if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
							AsyncProcessor.LOGGER.error("AsyncProcessor shutdown immediately due to wait timeout.");
							executor.shutdownNow();
						}
					} catch (InterruptedException e) {
						AsyncProcessor.LOGGER.error("AsyncProcessor shutdown interrupted.");
						executor.shutdownNow();
					}

					AsyncProcessor.LOGGER.info("AsyncProcessor shutdown complete.");
				}
			}));
		} catch (Exception e) {
			LOGGER.error("AsyncProcessor init error.", e);
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * 此类型无法实例化
	 */
	private AsyncProcessor() {
	}

	/**
	 * 执行任务，不管是否成功<br>
	 *
	 * @param task
	 * @return
	 */
	public static boolean executeTask(Runnable task) {

		try {
			executor.execute(task);
		} catch (RejectedExecutionException e) {
			LOGGER.error("Task executing was rejected.", e);
			return false;
		}

		return true;
	}

	/**
	 * 提交任务，并可以在稍后获取其执行情况<br>
	 * 当提交失败时，会抛出 {@link }
	 * 
	 * @param task
	 * @return
	 */
	public static <T> Future<T> submitTask(Callable<T> task) {

		try {
			return executor.submit(task);
		} catch (RejectedExecutionException e) {
			LOGGER.error("Task executing was rejected.", e);
			throw new UnsupportedOperationException("Unable to submit the task, rejected.", e);
		}
	}
}
