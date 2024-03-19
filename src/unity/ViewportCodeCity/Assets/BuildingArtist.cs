using System.Collections.Generic;
using TDLN.CameraControllers;
using UnityEngine;

public class BuildingArtist : MonoBehaviour, IArtist
{
    public Building buildingPrefab;

    public void Draw()
    {
        Debug.Log("This is when the Draw method of the BuildingArtist object read the repo's WorldData");
        WorldData worldData = BuildingDataRepository.Repository.WorldData;
        Debug.Log(worldData);
        List<BuildingInfo> buildingInfos = worldData.BuildingInfos;
        foreach (BuildingInfo buildingInfo in buildingInfos)
        {
            /* Places a cube with the BuildingInfo's location and size information */
            Building buildingObject = Instantiate<Building>(
                buildingPrefab,
                new Vector3(
                    buildingInfo.locX + buildingInfo.dimX / 2,
                    buildingInfo.locZ + buildingInfo.dimZ / 2,
                    buildingInfo.locY + buildingInfo.dimY / 2
                ),
                Quaternion.identity
            );

            buildingObject.Info = buildingInfo;
            

            buildingObject.transform.localScale = new Vector3(
                buildingInfo.dimX,
                buildingInfo.dimZ,
                buildingInfo.dimY
            );
        }
    }
}
