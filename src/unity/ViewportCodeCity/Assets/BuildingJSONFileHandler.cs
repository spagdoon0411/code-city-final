using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using UnityEngine;
using Newtonsoft.Json;

/* Takes a directory path and a file name on construct. Providesj
method for loading JSON object at that directory with that name into
native C# object for use in Unity.*/
public class BuildingJSONFileHandler // : MonoBehaviour
{
    private readonly string dirPath;
    private readonly string fileName;

    public BuildingJSONFileHandler(string dirPath, string fileName)
    {
        this.dirPath = dirPath;
        this.fileName = fileName;
    }

    public WorldData ReadFromFile()
    {
        /* Initalize buildings read to empty list in case of failure. */
        List<BuildingInfo> buildingsRead = new List<BuildingInfo>();

        /* Obtain the entire file path. Unity strictly uses forward slashes regardless of platform. */
        string fullPath = dirPath + "/" + fileName; // Path.Combine(dirPath, fileName);

        Debug.Log($"Looking for file at {fullPath}...");

        if (File.Exists(fullPath))
            try
            {
                /* Initialize JSON serial string for holding pure characters (hopefully JSON). */
                string serialJson = "";
                using (FileStream stream = new FileStream(fullPath, FileMode.Open))
                {
                    using (StreamReader reader = new StreamReader(stream))
                    {
                        /* Read entire JSON file using FileStreams and StreamnReaders that are dealloc'd after read (via using statements). */
                        serialJson = reader.ReadToEnd();
                    }
                }

                Debug.Log($"Found file at {fullPath}");
                
                /* Parse JSON serial string to a List of BuildingInfos (JsonUtility takes care of parsing). */
                buildingsRead = JsonConvert.DeserializeObject<IEnumerable<BuildingInfo>>(serialJson).ToList();
            }
            catch (Exception e)
            {
                Debug.LogError("Got exception when loading data from file at: " + fullPath +
                               ". Moving on with empty building set. \n" + e);
            }
        else
            /* Notify if file is not found. */
            Debug.LogError("File at: " + fullPath + " does not exist. Moving on with empty building set.");

        /* Package data obtained in a single WorldData object to return. */
        WorldData dataRead = new WorldData(buildingsRead);

        return dataRead;
    }
}