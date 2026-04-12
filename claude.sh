#!/bin/zsh

CUSTOM_MODEL_NAME="qwen3-develop"

# 1. Check if the model already exists in Ollama
if ollama list | grep -q "$CUSTOM_MODEL_NAME"; then
    echo "Model '$CUSTOM_MODEL_NAME' already exists. Skipping build."
else
    echo "Model '$CUSTOM_MODEL_NAME' not found. Creating it now..."

    ollama create $CUSTOM_MODEL_NAME -f ModelFile

    echo "Custom model '$CUSTOM_MODEL_NAME' created."
fi

ollama launch claude --model $CUSTOM_MODEL_NAME