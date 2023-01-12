package com.kite.scouter.lolapi.dto;

public record LeagueEntryVO(
    String leagueId,
    String summonerId,
    String summonerName,
    String queueType,
    String tier,
    String rank,
    int leaguePoints,
    int wins,
    int losses,
    boolean hotStreak,
    boolean veteran,
    boolean freshBlood,
    boolean inactive,
    MiniSeriesVO miniSeries
)
{
}
