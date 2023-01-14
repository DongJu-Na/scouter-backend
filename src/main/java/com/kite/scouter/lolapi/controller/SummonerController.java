package com.kite.scouter.lolapi.controller;

import com.kite.scouter.global.enums.LOLBaseUrl;
import com.kite.scouter.lolapi.dto.LeagueEntryVO;
import com.kite.scouter.lolapi.dto.MatchesVO;
import com.kite.scouter.lolapi.dto.SummonerVO;
import com.kite.scouter.lolapi.service.SummonerService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/lol/api")
@RequiredArgsConstructor
public class SummonerController {

  private final SummonerService summonerService;

  private final WebClient webClient;

  @GetMapping("/getSummoners/{summonerName}")
  public Mono<ResponseEntity<SummonerVO>> getSummoners(@PathVariable final String summonerName) {
    return webClient.get()
        .uri(LOLBaseUrl.KR.getTitle() + "/lol/summoner/v4/summoners/by-name/".concat(summonerName))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .toEntity(SummonerVO.class);
  }

  @GetMapping("/getMatchId/{puuid}")
  public Mono<List> getMatchId(@PathVariable final String puuid) {
    return webClient.get()
        .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/by-puuid/".concat(puuid).concat("/ids"))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(List.class);
        //.flatMap(this::getMatchDetailMethod);

  }

  public Flux<Map> getMatchDetailMethod(String matchId) {
    return  webClient.get()
        .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/".concat(matchId))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(Map.class);
  }

  @GetMapping("/getMatchDetail/{matchId}")
  public Mono<Map> getMatchDetail(@PathVariable final String matchId) {
    return  webClient.get()
        .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/".concat(matchId))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Map.class);
  }

  @GetMapping("/getLeagueInfo/{encryptedSummonerId}")
  public Mono<Set> getLeagueInfo(@PathVariable final String encryptedSummonerId) {
    return  webClient.get()
        .uri(LOLBaseUrl.KR.getTitle() + "/lol/league/v4/entries/by-summoner/".concat(encryptedSummonerId))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Set.class);
  }

  @GetMapping("/getSummoners/test/{summonerName}")
  public void getSummonersTest(@PathVariable final String summonerName) {
     Mono<SummonerVO> result1 = webClient.get()
        .uri(LOLBaseUrl.KR.getTitle() + "/lol/summoner/v4/summoners/by-name/".concat(summonerName))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(SummonerVO.class);

     Mono<List> result2 = webClient.get()
        .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/by-puuid/".concat("sdlegeEHnOBTb50W3eGrOaps3eRus4G0z83E6Iof_BxRm-8vqCgfPKNT5jFrSe_i30M88DYMMuOIvQ").concat("/ids"))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(List.class);


     Mono<Set> result3 = webClient.get()
        .uri(LOLBaseUrl.KR.getTitle() + "/lol/league/v4/entries/by-summoner/".concat("uRtiP7MPouAOcFTEDxrNnKwnZ_nH2MEK4j-zBm5mnNBMIK4NeQYz8HWUfA"))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Set.class);

  }

}
