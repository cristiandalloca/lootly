#!/bin/bash

MODULE_NAME=$1
TEMPLATE_DIR="module-template"
TARGET_DIR="src/main/kotlin/com/cdbfkk/lootly/modules/$MODULE_NAME"

if [ -z "$MODULE_NAME" ]; then
  echo "Usage: ./create-module.sh <module-name>"
  exit 1
fi

if [ -d "$TARGET_DIR" ]; then
  echo "❌ Módulo '$MODULE_NAME' já existe em '$TARGET_DIR'"
  exit 1
fi

# Copia a estrutura base
echo "📁 Criando estrutura do módulo em '$TARGET_DIR'..."
cp -r $TEMPLATE_DIR $TARGET_DIR

# Substitui placeholders nos arquivos
find $TARGET_DIR -type f \( -name "*.kt" -o -name "*.md" -o -name "*.properties" \) -exec sed -i "" \
  -e "s/YourEntity/${MODULE_NAME^}/g" \
  -e "s/yourEntity/${MODULE_NAME,,}/g" {} +

# Renomeia arquivos que contenham o placeholder
find $TARGET_DIR -type f -name "*YourEntity*" | while read FILE; do
  NEW_NAME=$(echo "$FILE" | sed "s/YourEntity/${MODULE_NAME^}/g")
  mv "$FILE" "$NEW_NAME"
  echo "🔤 Renomeado: $FILE → $NEW_NAME"
done

echo "✅ Módulo '$MODULE_NAME' criado com sucesso."
