using System;
using Newtonsoft.Json;

[Serializable]
public class BuildingInfo
{
    // Position vector
    [JsonProperty("locX")] public float locX;

    [JsonProperty("locY")] public float locY;

    [JsonProperty("locZ")] public float locZ;

    // Dimension vector
    [JsonProperty("dimX")] public float dimX;

    [JsonProperty("dimY")] public float dimY;

    [JsonProperty("dimZ")] public float dimZ;
}