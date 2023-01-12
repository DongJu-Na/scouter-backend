package com.kite.scouter.lolapi.dto;

public record SummonerVO(
    String accountId,
    int profileIconId,
    Long revisionDate,
    String name,
    String id,
    String puuid,
    Long summonerLevel
)
{
}
