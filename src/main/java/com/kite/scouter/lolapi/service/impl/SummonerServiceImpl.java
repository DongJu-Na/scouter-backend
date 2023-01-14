package com.kite.scouter.lolapi.service.impl;

import com.kite.scouter.global.enums.LOLBaseUrl;
import com.kite.scouter.lolapi.dto.LeagueEntryVO;
import com.kite.scouter.lolapi.dto.MatchesVO;
import com.kite.scouter.lolapi.dto.SummonerVO;
import com.kite.scouter.lolapi.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SummonerServiceImpl implements SummonerService {

  private final WebClient webClient;

  @Override
  public Mono<ResponseEntity<SummonerVO>> getSummonerBySummonerName(String summonerName) {
    return webClient.get()
        .uri(LOLBaseUrl.KR.getTitle() + "/lol/summoner/v4/summoners/by-name/".concat(summonerName))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .toEntity(SummonerVO.class);
  }

  @Override
  public Mono<MatchesVO> getMatchesByPuuid(String puuid,
                                                      int start,
                                                      int count) {

    Mono<MatchesVO> result = webClient.get()
        .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/by-puuid/".concat(puuid).concat("/ids"))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(MatchesVO.class);

    System.out.println(result);

    return result;
  }

  @Override
  public Mono<Object[]> getMatchByMatchId(String matchId) {
    return  webClient.get()
        .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/".concat(matchId))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Object[].class);
  }

  @Override
  public Mono<ResponseEntity<LeagueEntryVO>> getLeagueInfo(String encryptedSummonerId) {
    return  webClient.get()
        .uri(LOLBaseUrl.KR.getTitle() + "/lol/league/v4/entries/by-summoner/".concat(encryptedSummonerId))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .toEntity(LeagueEntryVO.class);
  }

}
