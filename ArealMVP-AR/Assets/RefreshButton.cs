using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RefreshButton : MonoBehaviour
{

    public void RefreshClouds()
    {
        var obj = GameObject.Find("NativeGateway");
        CloudManager manager = obj.GetComponent<CloudManager>();
        manager.UpdateClouds();
    }
    
}
