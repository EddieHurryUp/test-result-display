#!/bin/bash

# GitHub Pages部署脚本
echo "🚀 开始部署到GitHub Pages..."

# 检查是否在main分支
current_branch=$(git branch --show-current)
if [ "$current_branch" != "main" ]; then
    echo "❌ 请在main分支上运行此脚本"
    exit 1
fi

# 创建gh-pages分支（如果不存在）
if ! git show-ref --verify --quiet refs/heads/gh-pages; then
    echo "📝 创建gh-pages分支..."
    git checkout --orphan gh-pages
    git rm -rf .
    git commit --allow-empty -m "Initial gh-pages commit"
    git push origin gh-pages
    git checkout main
else
    echo "✅ gh-pages分支已存在"
fi

# 准备部署内容
echo "📦 准备部署内容..."

# 创建临时目录
TEMP_DIR=$(mktemp -d)
cd "$TEMP_DIR"

# 克隆gh-pages分支
git clone -b gh-pages "git@github.com:EddieHurryUp/test-result-display.git" .
git remote add origin "git@github.com:EddieHurryUp/test-result-display.git"

# 复制需要的文件
echo "📋 复制文件..."
cp /Users/qijiaxi/IdeaProjects/learn-java/index.html .
cp -r /Users/qijiaxi/IdeaProjects/learn-java/README.md .
cp -r /Users/qijiaxi/IdeaProjects/learn-java/src/main/resources/static/* .

# 添加和提交
git add .
git commit -m "Deploy to GitHub Pages - $(date)"

# 推送到gh-pages分支
echo "📤 推送到GitHub Pages..."
git push origin gh-pages -f

# 清理
cd /Users/qijiaxi/IdeaProjects/learn-java
rm -rf "$TEMP_DIR"

echo "✅ 部署完成！"
echo "🌐 访问地址: https://EddieHurryUp.github.io/test-result-display/"