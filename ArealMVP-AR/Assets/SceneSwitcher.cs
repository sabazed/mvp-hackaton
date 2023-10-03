using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class SceneSwitcher : MonoBehaviour
{

    private static bool _drawing = false;

    public static void ChangeScene()
    {
        if (_drawing)
        {
            FinishDrawing();
        }
        else
        {
            StartDrawing();
        }
    }

    private static void StartDrawing()
    {
        SceneManager.LoadScene("Drawing/Assets/Scenes/Drawing");
        _drawing = true;
    }

    private static void FinishDrawing()
    {
        SceneManager.LoadScene("Samples/ARCore Extensions/1.39.0/Geospatial Sample/Scenes/Geospatial");
        GameObject.Find("NativeGateway").GetComponent<CloudManager>().Reinitialize();
        _drawing = false;
    }

}
