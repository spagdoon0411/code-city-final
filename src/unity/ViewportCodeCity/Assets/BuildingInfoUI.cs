using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UIElements;

public class BuildingInfoUI : MonoBehaviour
{
    private Label buildingInfoTextLabel;
    private Label buildingInfoPackageLabel;

    /* Makes this repository a singleton. */
    public static BuildingInfoUI Instance { get; private set; }

    /* OnEnable is run when the UI document's source UXML is loaded. This is when the labels
     are actually available, so the singleton class should be set up now. */
    void OnEnable()
    {
        /* Obtains the root of the UI document driving the UI */
        UIDocument doc = GetComponent<UIDocument>();
        VisualElement buildingInfoRoot = doc.rootVisualElement;
        
        /* Obtains the two labels within the UI doc */
        VisualElement buildingInfoGroup = buildingInfoRoot.Query<VisualElement>("BuildingInfoContainer");
        buildingInfoTextLabel = buildingInfoGroup.Query<Label>("Information");
        buildingInfoPackageLabel = buildingInfoGroup.Query<Label>("PackageName");

        Instance = this;
    }


    /* Displays the contents of a BuildingInfo on the screen. */
    public void DisplayBuildingInfo(BuildingInfo buildingInfo)
    {
        string className = "ExampleName";
        string infoString = $"dimX: {buildingInfo.dimX}\ndimY: {buildingInfo.dimY}\ndimZ: {buildingInfo.dimZ}";

        buildingInfoTextLabel.text = infoString;
        buildingInfoPackageLabel.text = className;
    }

    /* Clears the contents of the UI info display */
    public void Clear()
    {
        buildingInfoTextLabel.text = "";
        buildingInfoPackageLabel.text = "";
    }
}
