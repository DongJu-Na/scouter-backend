package com.kite.scouter.lolapi.dto;

public record MiniSeriesVO(
    int losses,
    String progress,
    int target,
    int wins
)
{
}
