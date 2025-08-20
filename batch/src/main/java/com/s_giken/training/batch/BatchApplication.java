package com.s_giken.training.batch;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.s_giken.training.batch.resolver.IResolver;

@SpringBootApplication
public class BatchApplication implements CommandLineRunner {
	private final IResolver resolver;

	/**
	 * SpringBoot エントリポイント
	 * 
	 * @param args コマンドライン引数
	 */
	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param resolver SpringBootから注入される 処理オブジェクト
	 */
	public BatchApplication(@Qualifier("allSQLResolver") IResolver resolver) {
		this.resolver = resolver;
	}

	/**
	 * メイン処理
	 * 
	 * @param args コマンドライン引数
	 */
	@Override
	public void run(String... args) throws Exception {
		resolver.resolve(args);
	}
}
