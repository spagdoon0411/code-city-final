using UnityEngine;

public class Building : MonoBehaviour
{
    public BuildingInfo Info { get; set; }

    private Material buildingMaterial;

    public Color hoverColor;
    public Color clickColor;
    public Color restColor;

    void Start()
    {
        buildingMaterial = GetComponent<MeshRenderer>().material;
    }

    private void OnMouseDown()
    {
        buildingMaterial.SetColor("_FresnelColor", clickColor);
    }

    private void OnMouseUp()
    {
        buildingMaterial.SetColor("_FresnelColor", hoverColor);
    }

    /* Provides the UI element this building's info struct to display
     and highlights the building. */
    private void OnMouseEnter()
    {
        Debug.Log($"At line 34, BuildingInfoUI.Instance is {BuildingInfoUI.Instance}");
        buildingMaterial.SetColor("_FresnelColor", hoverColor);
        BuildingInfoUI.Instance.DisplayBuildingInfo(Info);
    }

    /* Unhighlights the in-game representation and clears the info UI element. */
    private void OnMouseExit()
    {
        buildingMaterial.SetColor("_FresnelColor", restColor);
        BuildingInfoUI.Instance.Clear();
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
