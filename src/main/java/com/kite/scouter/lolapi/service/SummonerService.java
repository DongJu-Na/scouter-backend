package com.kite.scouter.lolapi.service;

import com.kite.scouter.lolapi.dto.LeagueEntryVO;
import com.kite.scouter.lolapi.dto.SummonerVO;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface SummonerService {

  Mono<ResponseEntity<SummonerVO>> getSummonerBySummonerName(String summonerName);

  Mono<List> getMatchesByPuuid(String puuid,
                                               int start,
                                               int count);

  Mono<Object[]> getMatchByMatchId(String matchId);

  Mono<ResponseEntity<LeagueEntryVO>> getLeagueInfo(String encryptedSummonerId);

}
