using UnityEngine;
using UnityEngine.UI;

public class WidthSlider : MonoBehaviour
{
    
    public ParamsContainer paramsContainer;
    public Image background, foreground, handle;
    
    // Start is called before the first frame update
    void Start()
    {
        paramsContainer.onColorChange.AddListener(OnColorChange);
        paramsContainer.Size = GetComponent<Slider>().value;
        OnColorChange(paramsContainer.Clr);
    }

    private void OnColorChange(Color color)
    {
        background.color = new Color(color.r, color.g, color.b, 0.85f);
        foreground.color = color;
        handle.color = color;
    }
}
