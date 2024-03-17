using System.Collections.Generic;
using TDLN.CameraControllers;
using UnityEngine;

public class BuildingArtist : MonoBehaviour, IArtist
{
    public GameObject buildingPrefab;

    public void Start()
    {
        Debug.Log("This is when the Start() method for BuildingArtist was run.");
    }

    public void Draw()
    {
        Debug.Log("This is when the Draw method of the BuildingArtist object read the repo's WorldData");
        WorldData worldData = BuildingDataRepository.Repository.WorldData;
        Debug.Log(worldData);
        List<BuildingInfo> buildingInfos = worldData.BuildingInfos;
        foreach (BuildingInfo buildingInfo in buildingInfos)
        {
            /* Places a cube with the BuildingInfo's location and size information */
            GameObject buildingObject = Instantiate<GameObject>(
                buildingPrefab,
                new Vector3(
                    buildingInfo.locX,
                    buildingInfo.locY,
                    buildingInfo.locZ
                ),
                Quaternion.identity
            );

            buildingObject.transform.localScale = new Vector3(
                buildingInfo.dimX,
                buildingInfo.dimY,
                buildingInfo.dimZ
            );
        }
    }
}
