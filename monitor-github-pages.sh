#!/bin/bash

# GitHub Pages存储监控脚本
echo "📊 GitHub Pages存储监控"
echo "========================"

# 获取当前仓库大小
REPO_SIZE=$(du -sh . | cut -f1)
REPO_SIZE_BYTES=$(du -s . | cut -f1)

# 转换为MB
REPO_SIZE_MB=$(echo "scale=2; $REPO_SIZE_BYTES / 1024" | bc)

# 计算使用百分比
USAGE_PERCENT=$(echo "scale=2; $REPO_SIZE_BYTES * 100 / 1048576" | bc)

echo "📈 当前使用情况:"
echo "  仓库大小: $REPO_SIZE"
echo "  使用百分比: $USAGE_PERCENT%"

# 判断状态
if (( $(echo "$USAGE_PERCENT > 90" | bc -l) )); then
    echo "🔴 状态: 危险！存储空间即将耗尽"
    echo "⚠️  建议: 立即清理不必要的文件"
elif (( $(echo "$USAGE_PERCENT > 70" | bc -l) )); then
    echo "🟡 状态: 警告！存储空间使用较多"
    echo "⚠️  建议: 考虑清理一些文件"
elif (( $(echo "$USAGE_PERCENT > 50" | bc -l) )); then
    echo "🟢 状态: 正常，但需要关注"
    echo "💡 建议: 定期检查存储使用"
else
    echo "🟢 状态: 健康"
    echo "✅ 存储空间充足"
fi

echo ""
echo "📋 优化建议:"

if (( $(echo "$REPO_SIZE_MB > 100" | bc -l) )); then
    echo "  1. 查找大文件: find . -type f -size +10M"
    echo "  2. 清理历史: git gc --aggressive"
    echo "  3. 压缩图片: 使用在线工具压缩"
fi

echo "  4. 检查.gitignore: 确保排除不必要的文件"
echo "  5. 定期清理: 删除临时文件和缓存"

echo ""
echo "🔍 快速检查命令:"
echo "  du -sh .                    # 查看仓库大小"
echo "  find . -type f -size +10M   # 查找大文件"
echo "  git status                  # 查看未跟踪文件"