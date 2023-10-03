using System;
using System.Collections.Generic;
using System.IO;
using Messages;
using Unity.VectorGraphics;
using UnityEngine;

namespace DefaultNamespace
{
    public class Clouds : MonoBehaviour
    {

        public GameObject cloud1;
        public GameObject cloud2;
        public GameObject cloud3;
        
        public VectorUtils.TessellationOptions tesselationOptions;
        
        public static Clouds Instance;

        private void Start()
        {
            tesselationOptions.MaxCordDeviation = float.MaxValue;
            tesselationOptions.MaxTanAngleDeviation = float.MaxValue;
            tesselationOptions.StepDistance = 1f;
            tesselationOptions.SamplingStepSize = 0.1f;
            
            if (Instance == null) {
                Instance = this;
                DontDestroyOnLoad(this.gameObject);
            }
            else if (Instance != this)
            {
                Destroy(gameObject);
            }
        }

        public void UpdateClouds(List<CloudDto> clouds)
        {
            var cls = new List<GameObject> {cloud1, cloud2, cloud3};
            for (int i = 0; i < 3; i++)
            {
                if (cls[i].transform.childCount > 0)
                {
                    Destroy(cls[i].transform.GetChild(0).gameObject);
                }
            }
            for (int i = 0; i < clouds.Count; i++)
            {
                var svg = SVGParser.ImportSVG(new StringReader(clouds[i].SvgContent.Replace("\\\"", "\"")));
                var sprite = VectorUtils.BuildSprite(VectorUtils.TessellateScene(svg.Scene, tesselationOptions), 100.0f, VectorUtils.Alignment.Center, Vector2.zero, 128, true);
                var cl = new GameObject { name = "SVGImage" + i };
                var renderer = cl.AddComponent<SpriteRenderer>();
                renderer.sprite = sprite;
                renderer.sortingLayerName = "Cloud";
                renderer.sortingOrder = 10;
                cl.transform.parent = cls[i].transform;
                cl.transform.localPosition = new Vector3(0, 0, 0);
                cl.transform.localRotation = new Quaternion(0, 1,0, 0);
                cl.transform.localScale = new Vector3(0.5f / 4.5f, 0.5f / 2.5f, 1);
            }
        }

    }
}