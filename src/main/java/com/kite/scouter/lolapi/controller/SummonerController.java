package com.kite.scouter.lolapi.controller;

import com.kite.scouter.lolapi.dto.LeagueEntryVO;
import com.kite.scouter.lolapi.dto.SummonerVO;
import com.kite.scouter.lolapi.service.SummonerService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/lol/api")
@RequiredArgsConstructor
public class SummonerController {

  private final SummonerService summonerService;

  @GetMapping("/getSummoners/{summonerName}")
  public Mono<ResponseEntity<SummonerVO>> getSummoners(@PathVariable final String summonerName) {
    return summonerService.getSummonerBySummonerName(summonerName);
  }

  @GetMapping("/getMatchId/{puuid}")
  public Mono<List> getMatchId(@PathVariable final String puuid,
                                               @RequestParam(value = "start", defaultValue = "0", required = false) final int start,
                                               @RequestParam(value = "count", defaultValue = "20", required = false) final int count) {
    return summonerService.getMatchesByPuuid(puuid, start, count);
  }

  @GetMapping("/getMatchDetail/{matchId}")
  public Mono<Object[]> getMatchDetail(@PathVariable final String matchId) {
    return summonerService.getMatchByMatchId(matchId);
  }

  @GetMapping("/getLeagueInfo/{encryptedSummonerId}")
  public Mono<ResponseEntity<LeagueEntryVO>> getLeagueInfo(@PathVariable final String encryptedSummonerId) {
    return summonerService.getLeagueInfo(encryptedSummonerId);
  }

}
