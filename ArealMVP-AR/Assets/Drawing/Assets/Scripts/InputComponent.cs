using UnityEngine;

public abstract class InputComponent : MonoBehaviour
{
 
    
    public ParamsContainer paramsContainer;

    protected void Init()
    {
        UpdateInput();
        paramsContainer.onColorChange.AddListener(ChangeColor);
        paramsContainer.onSizeChange.AddListener(ChangeWidth);
    }

    public void UpdateInput()
    {
        ChangeWidth(paramsContainer.Size);
        ChangeColor(paramsContainer.Clr);
    }

    public abstract void ChangeWidth(float size);

    public abstract void ChangeColor(Color color);

}
