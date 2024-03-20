using System.Collections.Generic;

/* Contains buildingInfos and provides possibility of containing
parallel information (e.g., tags). */
public class WorldData
{ 
    /* BuildingInfos should not be set by an outside actor;
    they are only set (and possibly mutated) on construct. */
    public List<BuildingInfo> BuildingInfos { get; private set; }
    
   /* Empty world constructor. */
   public WorldData()
   {
       BuildingInfos = new List<BuildingInfo>();
   }

   /* Constructor for when buildingInfos are found. */
   public WorldData(List<BuildingInfo> buildingInfos)
   {
       BuildingInfos = buildingInfos;
   }
}