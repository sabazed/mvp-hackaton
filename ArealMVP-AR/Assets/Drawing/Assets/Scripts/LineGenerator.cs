using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;

public class LineGenerator : InputComponent
{
    
    public GameObject linePrefab;

    public bool UIPressed { get; set; }
    private Stack<Line> _drawings;
    private bool _pressed;
    public Stack<Line> Drawings => _drawings;

    private void Start()
    {
        this.Init();
        _drawings = new Stack<Line>();
        UIPressed = false;
    }

    private void Update()
    {
        
        if (EventSystem.current.IsPointerOverGameObject() || UIPressed || paramsContainer.Writing) return;
        
        if (Input.GetMouseButtonDown(0))
        {
            GameObject newLine = Instantiate(linePrefab);
            Drawings.Push(newLine.GetComponent<Line>());
            _pressed = true;
        }

        if (_pressed && Input.GetMouseButton(0) && Drawings.Count > 0 && Drawings.Peek() != null)
        {
            Vector2 mousePos = Camera.main.ScreenToWorldPoint(Input.mousePosition);
            Drawings.Peek().UpdateLine(mousePos);
        }

        if (Input.GetMouseButtonUp(0))
        {
            _pressed = false;
        }
    }

    public void CleanCanvas()
    {
        while (Drawings.Count > 0)
        {
            Destroy(Drawings.Pop().gameObject);
        } 
    }
     
    public override void ChangeWidth(float width)
    {
        width *= 0.75f;
        linePrefab.GetComponent<Line>().lineRenderer.SetWidth(width, width);
    }

    public override void ChangeColor(Color color)
    {
        var lineRenderer = linePrefab.GetComponent<Line>().lineRenderer;
        lineRenderer.SetColors(color, color);
    }

}
