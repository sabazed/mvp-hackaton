using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Events;

[CreateAssetMenu]
public class ParamsContainer : ScriptableObject
{
    
    [System.Serializable] public class ColorChange : UnityEvent<Color> { }
    [System.Serializable] public class SizeChange : UnityEvent<float> { }

    public ColorChange onColorChange;
    public SizeChange onSizeChange;
    
    private Color _color;
    private float _size;
    private bool _writing = false;

    public Color Clr
    {
        get => _color;
        set { _color = value; onColorChange.Invoke(_color); }
    }

    public float Size
    {
        get => _size;
        set { _size = value; onSizeChange.Invoke(_size); }
    }

    public bool Writing
    {
        get => _writing;
        set => _writing = value;
    }
    
}
