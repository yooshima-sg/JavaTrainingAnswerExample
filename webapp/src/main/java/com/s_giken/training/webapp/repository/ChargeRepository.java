package com.s_giken.training.webapp.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import com.s_giken.training.webapp.model.entity.Charge;

public interface ChargeRepository {

	public static final Set<String> ALLOW_COLUMN_NAMES =
			Set.of("charge_id",
					"name",
					"amount",
					"start_date",
					"end_date");
	public static final Set<String> ALLOW_SORT_ORDER =
			Set.of("desc", "asc");

	/**
	 * 料金情報をすべて取得する
	 * 
	 * @return 料金情報のリスト
	 */
	public List<Charge> findAll();

	/**
	 * 料金IDに一致する料金情報を取得する
	 * 
	 * @param id 料金ID
	 * 
	 * @return 料金情報のOptional型
	 */
	public Optional<Charge> findById(Long Id);

	/**
	 * 料金名の一部から料金情報を取得する
	 * 
	 * @param chargeName 料金名の一部
	 * @param sortCol ソートしたい列名
	 * @param sortOrder ソート方法(昇順or降順)
	 * 
	 * @return 料金情報のリスト
	 */
	public List<Charge> findByChargeNameLikeWithOrder(
			String chargeName,
			String sortColName,
			String sortOrder);

	/**
	 * 料金情報を追加する
	 * 
	 * @param charge 料金情報エンティティオブジェクト
	 * 
	 * @returnt 追加した料金情報の数
	 */
	public int add(Charge charge);

	/**
	 * 料金情報を更新する
	 * 
	 * @param charge 更新する情報が格納された料金情報エンティティオブジェクト
	 * 
	 * @return 更新した料金情報の数
	 */
	public int update(Charge cahrge);

	/**
	 * 料金IDを持つ料金情報を削除する
	 * 
	 * @param id 料金情報ID
	 * 
	 * @return 削除した料金情報の数
	 */
	public int deleteById(Long id);
}
