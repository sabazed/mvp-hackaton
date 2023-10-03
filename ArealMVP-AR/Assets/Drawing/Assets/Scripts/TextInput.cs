using System;
using System.Collections.Generic;
using TMPro;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.EventSystems;

public class TextGenerator : InputComponent
{

    private static readonly float SIZE_RATIO = 150f;
    
    public GameObject canvasObj;
    private Canvas _canvas;
    
    public GameObject textPrefab;
    public GameObject textObj, placeholderObj;
    private TMP_Text _text, _placeholder;
    private TMP_InputField _input;

    public GameObject UIPressed { get; set; }
    private Stack<GameObject> _texts;
    public Stack<GameObject> Texts => _texts;

    private void Start()
    {
        _texts = new Stack<GameObject>();
        _text = textObj.GetComponent<TextMeshProUGUI>();
        _placeholder = placeholderObj.GetComponent<TextMeshProUGUI>();
        _input = GetComponent<TMP_InputField>();
        _canvas = canvasObj.GetComponent<Canvas>();
        gameObject.SetActive(false);
        this.Init();
    }

    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Return) &&
            !(Input.GetKey(KeyCode.LeftShift) || Input.GetKeyDown(KeyCode.LeftShift)))
        {
            _input.text = _input.text.Replace("\n", "");
            FinishWriting(true);
        }
    }

    public void CleanCanvas()
    {
        while (Texts.Count > 0)
        {
            Destroy(Texts.Pop());
        }

        FinishWriting(true);
    }

    public void StartWriting()
    {
        gameObject.SetActive(true);
        paramsContainer.Writing = true;
        _input.Select();
        _input.ActivateInputField();
    }

    public void FinishWriting(bool clear = false)
    {
        if (!clear && EventSystem.current.IsPointerOverGameObject()) return;
        CreateText();
        _input.text = "";
        _text.text = "";
        gameObject.SetActive(false);
        paramsContainer.Writing = false;
    }

    private void CreateText()
    {
        Debug.Log(canvasObj);
        GameObject o = Instantiate(textPrefab, canvasObj.transform, false);
        TMP_Text nextText = o.GetComponent<TMP_Text>();

        nextText.text = _text.text;
        nextText.color = paramsContainer.Clr;
        nextText.fontSize = paramsContainer.Size * SIZE_RATIO;
        nextText.faceColor = paramsContainer.Clr;

        var rect = o.GetComponent<RectTransform>();
        rect.sizeDelta = new Vector2(nextText.preferredWidth, nextText.preferredHeight);

        Text txt = o.GetComponent<Text>();
        txt._Canvas = _canvas;
        
        Texts.Push(o);
    }

    public override void ChangeWidth(float size)
    {
        _text.fontSize = size * SIZE_RATIO;
        _placeholder.fontSize = size * SIZE_RATIO;
    }

    public override void ChangeColor(Color color)
    {
        _text.color = color;
        _placeholder.color = color;
    }

}
