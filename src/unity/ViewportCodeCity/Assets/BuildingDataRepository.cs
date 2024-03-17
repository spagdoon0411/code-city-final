using System.IO;
using TDLN.CameraControllers;
using UnityEngine;

public class BuildingDataRepository : MonoBehaviour, IArtistManager
{
    /* JSON file path configuration fields accessible from the Unity editor. */
    [Header("File Storage Config")]
    public string fileName = "test.json";
    public string dirPath;
    
    /* Place for a single observer-like building artist. Accessible from Unity editor. */
    public BuildingArtist buildingArtist;
    
    /* Internal reference providing interface to JSON file of buildings.*/
    private BuildingJSONFileHandler handler;
    
    /* The worldData of the repository can only be accessed or mutated. */
    public WorldData WorldData
    { get; private set; }

    /* Attribute providing repository access. Cannot be set. */
    public static BuildingDataRepository Repository { get; private set; }

    /* This WOULD go into a constructor, but Unity has its own construction protocol
     for GameObjects. */
    public void Start()
    {
         // Use the current working directory as the place to look for building info to load.
         dirPath = Directory.GetCurrentDirectory();
         Debug.Log($"Current working directory is: {dirPath}");
         Debug.Log($"Current file name set is: {fileName}");
        
         /* Sets the singleton reference to this instance if it's not set. If it is is set,
          throws an error; there should only be one BuildingRepository instance. */
         if (Repository == null)
         {
             Repository = this;
         }
         else
         {
             Debug.LogError("More than one BuildingRepository was found.");
         }
         
         handler = new BuildingJSONFileHandler(dirPath, fileName);
         
         LoadSession();
         
         // TODO: remove before deploying.
         foreach (var buildingInfo in WorldData.BuildingInfos)
             Debug.Log(
                 $"Building found with size \n\t{buildingInfo.dimX} \n\t{buildingInfo.dimY} \n\t{buildingInfo.dimZ} \nat location \n\t{buildingInfo.locX} \n\t{buildingInfo.locY} \n\t{buildingInfo.locZ}.");
         
         CommissionArtist();
    }

    /* From the Observer design pattern; calls Draw on the observing artist. */
    public void CommissionArtist()
    {
        buildingArtist.Draw();
    }
    
    /* Loads a session from the provided path with the file handler. */
    private void LoadSession()
    {
        if (WorldData == null)
        {
            Debug.Log("World data was not loaded; using empty world.");

            WorldData = new WorldData();
        }

        WorldData = handler.ReadFromFile();
        Debug.Log("ReadFromFile returned " + WorldData + " on line 76 in BuildingDataRepository.");
        Debug.Log("This is when the worldData object of the repo was set.");
    }
}