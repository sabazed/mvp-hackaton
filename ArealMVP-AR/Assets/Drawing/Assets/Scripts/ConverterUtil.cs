
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using TMPro;
using UnityEngine;

public class ConverterUtils
{

    private static readonly float SCALE_FACTOR = 109.25f;
    
    public static string ConvertToSVG(Stack<Line> lines, Stack<GameObject> texts)
    {
        lines = new Stack<Line>(lines.Reverse());
        var res = "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">";
        
        var minX = float.MaxValue;
        var maxY = float.MinValue;
        // Find maximal right and top points
        foreach (var line in lines)
        {
            var lineRenderer = line.lineRenderer;
            var positionCount = lineRenderer.positionCount;
            if (positionCount < 2) continue;
            
            for (var i = 0; i < lineRenderer.positionCount; i++)
            {
                Vector2 pos = lineRenderer.GetPosition(i);
                minX = Math.Min(pos.x, minX);
                maxY = Math.Max(pos.y, maxY);
            }
        }


        // foreach (GameObject textObject in texts)
        // {
        //     RectTransform transform = textObject.GetComponent<RectTransform>();
        //     Vector2 position = transform.anchoredPosition;
        //     minX = Math.Min((position.x - transform.rect.width / 2) / SCALE_FACTOR, minX);
        //     maxY = Math.Max((position.y + transform.rect.height / 2) / SCALE_FACTOR, maxY);
        // }
        
        // Move all points to the right and bottom with minX and maxY
        // Store negative y coords to flip them
        foreach (var line in lines)
        {
            var lineRenderer = line.lineRenderer;
            var positionCount = lineRenderer.positionCount;
            if (positionCount < 2) continue;

            var x = lineRenderer.GetPosition(0).x;
            var y = lineRenderer.GetPosition(0).y;
            x -= minX;
            y -= maxY;
            
            res += $"<path d=\"M{x * SCALE_FACTOR},{-y * SCALE_FACTOR}";
            for (var i = 0; i < lineRenderer.positionCount; i++)
            {
                x = lineRenderer.GetPosition(i).x;
                y = lineRenderer.GetPosition(i).y;
                x -= minX;
                y -= maxY;
                res += $"L{x * SCALE_FACTOR},{-y * SCALE_FACTOR}";
            }
            res += $"\" stroke=\"#{ColorUtility.ToHtmlStringRGB(lineRenderer.startColor)}\" fill=\"none\" stroke-width=\"{lineRenderer.startWidth * SCALE_FACTOR}\" />";
        }
        
        // foreach (GameObject textObject in texts)
        // {
        //     // Get the text and position
        //     TMP_Text text = textObject.GetComponent<TMP_Text>();
        //     if (text.text.Length == 0) continue;
        //     RectTransform transform = textObject.GetComponent<RectTransform>();
        //     Vector2 position = transform.anchoredPosition;
        //
        //     // Convert position to SVG coordinates (assuming Y is up in SVG)
        //     var x = position.x - transform.rect.width / 2;
        //     var y = position.y - transform.rect.height / 2;
        //     x -= minX * SCALE_FACTOR;
        //     y -= maxY * SCALE_FACTOR;
        //     
        //     // Create a text element in SVG
        //     _writer.WriteLine($"<text x=\"{x}\" y=\"{-y}\" font-family=\"{text.font}\" font-size=\"{text.fontSize}\" fill=\"#{ColorUtility.ToHtmlStringRGB(text.color)}\">{text.text}</text>");
        // }

        res += "</svg>";
        return res;
    }
    
}

