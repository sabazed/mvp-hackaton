using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Converter : MonoBehaviour
{
    public LineGenerator LineGenerator;
    public TextGenerator TextGenerator;

    public RNScript gateway;

    private void Start()
    {
        gateway = GameObject.Find("NativeGateway").GetComponent<RNScript>();
    }

    private void Update()
    {
        if (Input.GetKey(KeyCode.Escape))
        {
            SceneSwitcher.ChangeScene();
        }
    }

    public void CreateSVG()
    {
        gateway.PublishCloud(ConverterUtils.ConvertToSVG(LineGenerator.Drawings, TextGenerator.Texts));
    }
    
}
