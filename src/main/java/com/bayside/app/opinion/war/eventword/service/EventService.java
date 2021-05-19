package com.bayside.app.opinion.war.eventword.service;

import java.util.List;

import com.bayside.app.opinion.war.eventword.model.NegativeWord;
import com.bayside.app.opinion.war.eventword.model.Trade;
import com.bayside.app.opinion.war.eventword.model.WeiDu;

public interface EventService {
	 List<Trade> selectAllTrade();
	 List<WeiDu> selectByTradeId(String tradeid);
	 List<NegativeWord> selectByWeiDuId(String weiduid);
	 Trade selectByTradeName(String name);
	 List<WeiDu> selectByTradeByName(String name);
}
