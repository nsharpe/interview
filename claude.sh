#!/bin/zsh

echo "Select Model"
echo "qwen3-coder-next:latest (1, or press enter)"
echo "qwen2.5-coder:32b (2)"

read select\?"? ";
case $select in
  2) ollama launch claude --model qwen2.5-coder:32b ;;
  1|*) ollama launch claude --model qwen3-coder-next:latest ;;
esac