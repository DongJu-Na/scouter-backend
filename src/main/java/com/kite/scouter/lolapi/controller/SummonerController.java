package com.kite.scouter.lolapi.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kite.scouter.global.enums.LOLBaseUrl;
import com.kite.scouter.global.enums.ResponseCode;
import com.kite.scouter.global.exception.BadRequestException;
import com.kite.scouter.global.utils.ObjectUtil;
import com.kite.scouter.lolapi.dto.SummonerVO;

import lombok.RequiredArgsConstructor;
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
  
  @GetMapping("/getLeagueInfo/{encryptedSummonerId}")
  public Mono<ResponseEntity<Set>> getLeagueInfo(@PathVariable final String encryptedSummonerId) {
    return  webClient.get()
        .uri(LOLBaseUrl.KR.getTitle() + "/lol/league/v4/entries/by-summoner/".concat(encryptedSummonerId))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .toEntity(Set.class);
  }

  @GetMapping("/getMatchId/{puuid}")
  public Mono<ResponseEntity<List>> getMatchId(
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
        .toEntity(List.class);
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

  @GetMapping("/getMatchesInfo/{puuid}")
  private List getMatchesInfo(
      @PathVariable final String puuid,
      @RequestParam(value = "start", defaultValue = "0", required = false) final int start,
      @RequestParam(value = "count", defaultValue = "10", required = false) final int count
  ) throws Exception {

    List<Map> result = new ArrayList<>();

    CountDownLatch latch = new CountDownLatch(count);

    List matchList =
      webClient.get()
        .uri(uriBuilder ->
              uriBuilder
                .scheme("https")
                .host("asia.api.riotgames.com")
                .path("/lol/match/v5/matches/by-puuid/".concat(puuid).concat("/ids"))
                .queryParam("start",start)
                .queryParam("count",count)
                .build()
            )
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(List.class)
        .block();

    if(ObjectUtil.isEmpty(matchList)){
      throw BadRequestException.of(ResponseCode.LY003, "lol.matchId.not.find");
    }

    getMatchInfo(
      result,
      matchList,
      latch
    );
    waitThreads(latch);
    return result;
  }

  private void getMatchInfo(List<Map> result, List<String> matchIds, CountDownLatch latch) {
    matchIds.stream()
      .forEach(
        o -> webClient.get()
          .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/".concat(o.toString()))
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(Map.class)
          .subscribe(map -> {
                result.add(map);
                latch.countDown();
          })
      );
  }

  private void waitThreads(CountDownLatch latch) throws InterruptedException {
    if (!latch.await(2, TimeUnit.SECONDS)) {
      throw new RuntimeException();
    }
  }

  public Flux<Map> getMatchDetailMethod(String matchId) {
    return  webClient.get()
        .uri(LOLBaseUrl.ASIA.getTitle() + "/lol/match/v5/matches/".concat(matchId))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(Map.class);
  }
  
  @SuppressWarnings("unchecked")
  @GetMapping("/getRankingInfo")
  public List<?> getRankingInfo() throws Exception{
	  List<Map<String,Object>> result = new ArrayList<>();
	  ObjectMapper obm = new ObjectMapper();
	  Object challengerleaguesObj = null,grandmasterleaguesObj = null,masterleaguesObj = null;
	  Map<String, Object> challengerleagues = null,grandmasterleagues = null,masterleagues = null;
	  
	  try {
		  challengerleaguesObj = webClient.get()
				  .uri(uriBuilder ->uriBuilder
						  .scheme("https")
						  .host("kr.api.riotgames.com")
						  .path("/lol/league/v4/challengerleagues/by-queue/".concat("RANKED_SOLO_5x5"))
						  .build()
						  )
				  .accept(MediaType.APPLICATION_JSON)
				  .retrieve()
				  .bodyToMono(Object.class)
				  .block();
		  
		  grandmasterleaguesObj = webClient.get()
				  .uri(uriBuilder ->uriBuilder
						  .scheme("https")
						  .host("kr.api.riotgames.com")
						  .path("/lol/league/v4/grandmasterleagues/by-queue/".concat("RANKED_SOLO_5x5"))
						  .build()
						  )
				  .accept(MediaType.APPLICATION_JSON)
				  .retrieve()
				  .bodyToMono(Object.class)
				  .block();
		  
		  masterleaguesObj = webClient.get()
				  .uri(uriBuilder ->uriBuilder
						  .scheme("https")
						  .host("kr.api.riotgames.com")
						  .path("/lol/league/v4/masterleagues/by-queue/".concat("RANKED_SOLO_5x5"))
						  .build()
						  )
				  .accept(MediaType.APPLICATION_JSON)
				  .retrieve()
				  .bodyToMono(Object.class)
				  .block();
		  
		  challengerleagues = obm.convertValue(challengerleaguesObj, Map.class);
		  grandmasterleagues = obm.convertValue(grandmasterleaguesObj, Map.class);
		  masterleagues = obm.convertValue(masterleaguesObj, Map.class);
		  		  
		  
		  
		  result = Stream
				  	   .of(put2KeyMap((List<Map<String,Object>>) challengerleagues.get("entries"), "tier", "CHALLENGER")
						  ,put2KeyMap((List<Map<String,Object>>) grandmasterleagues.get("entries"), "tier", "GRANDMASTER")
						  ,put2KeyMap((List<Map<String,Object>>) masterleagues.get("entries"), "tier", "MASTER"))
				  .flatMap(Collection::stream)
				  .collect(Collectors.toList());
		  
		  if(result != null && result.size() > 0) {
			  result = result.stream()
			  .sorted((v1 , v2) -> Integer.compare((int)v2.get("leaguePoints"), (int)v1.get("leaguePoints")))
			  .collect(Collectors.toList());
			  
		  }
				  
		  
	} catch (Exception e) {
		e.printStackTrace();
	}
	  
	   
	  return result;
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
private List<Map<String,Object>> put2KeyMap(List<Map<String, Object>> map , String key , String value) {
	  List result = new ArrayList<>();
	  Map<String, Object> tempMap = new HashMap<>();
	  
	try {
		 for (int i = 0; i < map.size(); i++) {
			 tempMap =  (Map<String, Object>) map.get(i);
			 tempMap.put(key, value);
			 result.add(tempMap);
		 }
	} catch (Exception e) {
		result = null;
		e.printStackTrace();
	}
	  
	  return result;
  }






}
