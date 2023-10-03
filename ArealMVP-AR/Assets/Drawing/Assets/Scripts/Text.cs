using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;

public class Text : MonoBehaviour
{

    private Canvas _canvas;

    public Canvas _Canvas
    {
        get => _canvas;
        set => _canvas = value;
    }
    
    public void DragHandler(BaseEventData data)
    {
        PointerEventData pointerData = (PointerEventData) data;

        Vector2 position;
        RectTransformUtility.ScreenPointToLocalPointInRectangle(
            (RectTransform) _canvas.transform, 
            pointerData.position,
            _canvas.worldCamera, 
            out position
        );

        transform.position = _canvas.transform.TransformPoint(position);
    }
    
}
