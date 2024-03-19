using System;
using System.Collections;
using System.Collections.Generic;
using TDLN.CameraControllers;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.Rendering;
using UnityEngine.Rendering.Universal;

public class PostProcessingVolume : MonoBehaviour
{
    public CameraOrbit cameraOrbit;

    // private CameraOrbit orbitInfo;

    public Volume volume;

    private DepthOfField depthOfFieldSettings;

   
    void Start()
    {
        VolumeProfile volProf = volume.profile;
        
        Debug.Log($"Found volume profile: {volProf}");

        if(!volProf.TryGet<DepthOfField>(out DepthOfField depthOfFieldSettingReceived))
        {
             Debug.Log("Could not find depth of field settings.");
        }

        depthOfFieldSettings = depthOfFieldSettingReceived;
    }

    // Update is called once per frame
    void Update()
    {
        depthOfFieldSettings.focusDistance.value = cameraOrbit.distance;
    }
}
