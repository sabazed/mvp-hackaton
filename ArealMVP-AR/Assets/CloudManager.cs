using System.Collections.Generic;
using DefaultNamespace;
using Messages;
using UnityEngine;

public class CloudManager : MonoBehaviour
{

    public RNScript gateway;
    private List<CloudDto> _clouds;
    private bool initial = true;
    
    // Update is called once per frame
    void Update()
    {
        if (initial)
        {
            if (_clouds == null)
            {
                UpdateClouds();
            }
            else
            {
                initial = false;
            }
        }
    }

    public void Reinitialize()
    {
        initial = true;
        _clouds = null;
    }

    public void UpdateClouds()
    {
        _clouds = gateway.FetchClouds();
        Clouds.Instance.UpdateClouds(_clouds);
    }
}
