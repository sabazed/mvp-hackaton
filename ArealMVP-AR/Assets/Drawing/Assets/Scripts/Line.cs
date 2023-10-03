using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Linq;

public class Line : MonoBehaviour
{

    public LineRenderer lineRenderer;
    private List<Vector2> _points;

    public void UpdateLine(Vector2 position)
    {
        if (_points == null)
        {
            _points = new List<Vector2>();
            SetPoint(position);
        }
        else if (Vector2.Distance(_points.Last(), position) > .1f)
        {
            SetPoint(position);
        }
    }

    void SetPoint(Vector2 point)
    {
        _points.Add(point);
        lineRenderer.positionCount = _points.Count;
        lineRenderer.SetPosition(_points.Count - 1, point);
    }

}
