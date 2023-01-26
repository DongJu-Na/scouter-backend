package com.kite.scouter.lolapi.controller;

import com.kite.scouter.global.enums.LOLBaseUrl;
import com.kite.scouter.lolapi.dto.SummonerVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/api/lol")
@RequiredArgsConstructor
public class SummonerController {


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
  public ResponseEntity<List> getMatchId(
      @PathVariable final String puuid,
      @RequestParam(value = "startTime",  required = false) final Long startTime,
      @RequestParam(value = "endTime",  required = false) final Long endTime,
      @RequestParam(value = "queue",  required = false) final int queue,
      @RequestParam(value = "type",  required = false) final String type,
      @RequestParam(value = "start", defaultValue = "0", required = false) final int start,
      @RequestParam(value = "count", defaultValue = "20", required = false) final int count
  ) {

    return webClient.get()
        .uri(uriBuilder ->
            uriBuilder.path(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/by-puuid/".concat(puuid).concat("/ids"))
                .queryParam("startTime",startTime)
                .queryParam("endTime",endTime)
                .queryParam("queue",queue)
                .queryParam("type",type)
                .queryParam("start",start)
                .queryParam("count",count)
                .build()
        )
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .toEntity(List.class)
        .block();
  }

  @GetMapping("/getMatchesInfo/{puuid}")
  public List getMatchIdTest(
      @PathVariable final String puuid,
      @RequestParam(value = "start", defaultValue = "0", required = false) final int start,
      @RequestParam(value = "count", defaultValue = "20", required = false) final int count
  ) {
    List<Map> resutl = new ArrayList<>();

    webClient.get()
        .uri(uriBuilder ->
            uriBuilder.path(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/by-puuid/".concat(puuid).concat("/ids"))
                .queryParam("start",start)
                .queryParam("count",count)
                .build()
        )
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(List.class)
        .block()
        .stream().forEach(
            o -> {
              resutl.add(webClient.get()
                  .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/".concat(o.toString()))
                  .accept(MediaType.APPLICATION_JSON)
                  .retrieve()
                  .bodyToMono(Map.class)
                  .block());

        });

        return resutl;

  }
  /*
  @GetMapping("/getMatchId/test2/{puuid}")
  public Flux getMatchIdTest2(@PathVariable final String puuid) {


    return webClient.get()
        .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/by-puuid/".concat(puuid).concat("/ids"))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(List.class)
        .block()
        .stream().flatMap(o -> {
                  webClient.get()
                      .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/".concat(o.toString()))
                      .accept(MediaType.APPLICATION_JSON)
                      .retrieve()
                      .bodyToMono(Map.class);
            }

    //return resutl;

  }*/

  public Flux<Map> getMatchDetailMethod(String matchId) {
    return  webClient.get()
        .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/".concat(matchId))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(Map.class);
  }

  @GetMapping("/getMatchDetail/{matchId}")
  public ResponseEntity<Map> getMatchDetail(@PathVariable final String matchId) {
    return  webClient.get()
        .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/".concat(matchId))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .toEntity(Map.class)
        .block();
  }

  @GetMapping("/getLeagueInfo/{encryptedSummonerId}")
  public ResponseEntity<Set> getLeagueInfo(@PathVariable final String encryptedSummonerId) {
    return  webClient.get()
        .uri(LOLBaseUrl.KR.getTitle() + "/lol/league/v4/entries/by-summoner/".concat(encryptedSummonerId))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .toEntity(Set.class)
        .block();
  }

  @GetMapping("/getSummoners/test/{summonerName}")
  public void getSummonersTest(@PathVariable final String summonerName) {
     SummonerVO result1 = webClient.get()
        .uri(LOLBaseUrl.KR.getTitle() + "/lol/summoner/v4/summoners/by-name/".concat(summonerName))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(SummonerVO.class)
        .block();

     List result2 = webClient.get()
        .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/by-puuid/".concat("sdlegeEHnOBTb50W3eGrOaps3eRus4G0z83E6Iof_BxRm-8vqCgfPKNT5jFrSe_i30M88DYMMuOIvQ").concat("/ids"))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(List.class)
        .block();


     Set result3 = webClient.get()
        .uri(LOLBaseUrl.KR.getTitle() + "/lol/league/v4/entries/by-summoner/".concat("uRtiP7MPouAOcFTEDxrNnKwnZ_nH2MEK4j-zBm5mnNBMIK4NeQYz8HWUfA"))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Set.class)
         .block();

  }

}
