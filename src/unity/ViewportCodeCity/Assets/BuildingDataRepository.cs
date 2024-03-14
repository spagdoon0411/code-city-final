using System.IO;
using UnityEngine;

public class BuildingDataRepository : MonoBehaviour
{
    [Header("File Storage Config")]
    /* [SerializeField]*/ public string fileName = "test.json";

    private string dirPath;

    /* Internal reference providing interface to JSON file of buildings.*/
    private BuildingJSONFileHandler handler;

    private WorldData worldData;

    /* Attribute providing repository access. Cannot be set. */
    public static BuildingDataRepository Repository { get; private set; }

    /* Building repository is created on application startup. */
    public void Start()
    {
        Create();
        LoadSession();

        // TODO: remove before deploying.
        foreach (var buildingInfo in worldData.BuildingInfos)
            Debug.Log(
                $"Building found with size \n\t{buildingInfo.dimX} \n\t{buildingInfo.dimY} \n\t{buildingInfo.dimZ} \nat location \n\t{buildingInfo.locX} \n\t{buildingInfo.locY} \n\t{buildingInfo.locZ}.");
    }

    /* Repository initialization sequence, to be run on application startup. */
    private void Create()
    {
        // Use the current working directory as the place to look for building info to load.
        dirPath = Directory.GetCurrentDirectory();
        Debug.Log($"Current working directory is: {dirPath}");
        Debug.Log($"Current file name set is: {fileName}");

        Repository = this;
        handler = new BuildingJSONFileHandler(dirPath, fileName);
    }

    /* Loads a session from the provided path with the file handler. */
    public void LoadSession()
    {
        if (worldData == null)
        {
            Debug.Log("World data was not loaded; using empty world.");

            worldData = new WorldData();
        }

        worldData = handler.ReadFromFile();
    }
}