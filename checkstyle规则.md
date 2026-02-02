---
alwaysApply: true
notes:
若在每次对话时问题需触发此规则，请将上方的”alwaysApply: false”中的false改为true，如本规则不需要跟随项目进行commit，请在.gitignore文件中添加该规则文件。
---

# Java Checkstyle 代码生成规范

## 核心原则
**生成代码需在脑海中完成检查，而不是生成后再修改**

---

## 两大致命错误

### 1. 行尾空格
**每一行末尾不能有任何空格或Tab**

### 2. Import顺序错误
**需参见：分组错误、组内排序错误、缺少空行**

--

## Import排序完整规范

### 核心规则

**1. 6大组结构（组间必须有空行）**

静态导入 (空行) java (空行) javax (空行) org (空行) com (空行) 其他

**2. 每个大组是一个整体，组内按.分段逐段字母排序，无需空行**

### Import完整范例

```java
// 静态导入组 (内部按字母排序，无需空行)
import static com.kwai.threshold.ThresholdUtil.check;
import static com.kwai.zt.domain.util.Result.success;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.BooleanUtils.toBoolean;

// java组
import java.io.InputStream;
import java.util.Map;

// javax组
import javax.annotation.Nonnull;

// org组
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

// com组 (按.分段逐段字母排序)
import com.kwai.infra.common.threshold.IThreshold;
import com.kwai.framework.redis.KwaiPROJedisClusterConfig;
import com.kwai.threshold.ThresholdChecker;
import com.kwai.topic.model.RequestProperty;
import com.kwai.user.sdk.service.UserService;
import com.kwai.zt.domain.config.EnvtagsExecuteThreshold;

// 其他组 (除了静态导入，java，javax，org，com开头的其他，内部按.分段逐段字母排序)
import eu.bitwalker.useragentutils.Browser;
import io.netty.buffer.ByteBuf;
import kuaishou.common.BizDef;
```

## 两大错误

**错误1：6大组间必须有空行**

```java
✅ import static xxx;      // 静态导入组

✅ import java.xxx;        // java组 (必须空行)

✅ import javax.xxx;       // javax组 (必须空行)

✅ import org.xxx;         // org组 (必须空行)

✅ import com.xxx;         // com组 (必须空行)

✅ import _eu.xxx;         // 其他组 (必须空行)
```

**错误2：每个大组内部按.分段逐段字母排序，无需空行**

✅ **静态导入组内部无需空行**
```java
import static com.kwai.xxx;
import static java.util.xxx;       // 无需空行
import static org.apache.xxx;      // 无需空行
```

✅ **其他组内部**
```java
import eu.bitwalker.xxx;
import io.netty.xxx;               // 无需空行
import kuaishou.common.xxx;        // 无需空行
```

## 分段字母排序规则

**按.分段，逐段字母比较**

**分段比较示例**
```
com.kwai.threshold   -> [com][kwai][threshold]
com.kwai.topic       -> [com][kwai][topic]
com.kwai.user        -> [com][kwai][user]
比较：前2段相同，第3段 t < u -> threshold在借
```

**同段逐字比较**
```
threshold vs topic: [t-h-r...] vs [t-o-p...] -> h<o -> threshold < topic
user vs util: [u-s-e...] vs [u-t-i...] -> s<t -> user < util
```

## A1最易犯的3个错误

**错误1：大组间缺空行**

❌
```java
import static com.kwai.xxx;
import java.util.Map;      // 错！静态组和java组间需空行
```
✅
```java
import static com.kwai.xxx;

import java.util.Map;
```

**错误2：组内多余空行**

❌
```java
import static com.kwai.xxx;

import static java.util.Collections.emptyMap;  // 错！静态组内部不需要空行
```
✅
```java
import static com.kwai.xxx;
import static java.util.Collections.emptyMap;  // 组内无需空行
```

**错误3：组内排序错误**

❌
```java
import com.kwai.topic.model.RequestProperty;
import com.kwai.user.sdk.service.UserService;
import com.kwai.threshold.ThresholdChecker;    // 错！t应该在u前
```
✅
```java
import com.kwai.threshold.ThresholdChecker;    // t-h
import com.kwai.topic.model.RequestProperty;   // t-o (h<o)
import com.kwai.user.sdk.service.UserService;  // u
```

## Import排序强制执行算法

**生成代码前必须执行的算法检查**

**步骤1：Import分组算法**

对于每个import语句：
1. 如果是“import static”开头 -> 静态导入组
2. 如果是“import java.”开头 -> java组
3. 如果是“import javax.”开头 -> javax组
4. 如果是“import org.”开头 -> org组
5. 如果是“import com.”开头 -> com组
6. 其他所有import -> 其他组

**步骤2：组间空行算法**

对于相邻的两个import语句：
if (当前import的组 != 下一个import的组) {
    在两者之间插入一个空行
}

**步骤3：组内排序算法**

对于同一组内的import语句：
1. 按包路径的字符串排序
2. 逐字符比较ASCII码
3. 例：`com.kwai.threshold < com.kwai.zt` (因为't' < 'z')


**、自动伦检查清单**

生成任何包含import的代码前，必须执行以下检查：

1. 第1步：所有import已按6大组正确分类？
2. 第2步：每个大组间有且仅有一个空行？
3. 第3步：每个大组内部按排序排好？
4. 第4步：每行末尾无空格？
5. 第5步：像com开头的包（如kuaishou.common）在“其他”组？

## 强制执行规则

**规则1：生成后强制检查**
在生成代码块及import代码块后，AI必须：
1. 将所有import按上述算法分组
2. 按组内部包名排序排序
3. 组间插入空行
4. 验证最终结果的正确性

**规则2：不知道怎么排**
不熟悉具体的包，而是依赖算法：
- 不论“threshold在zt前面”这种具体规则
- 只严格“字母按比较算法”
- 适用于任何包名组合

## 禁止事项

**禁用API**
```java
❌ System.out/err.println()  ✅ log.info/error()
❌ e.printStackTrace()
❌ Object.finalize()
```

**禁用的包**
```
❌ org.apache.commons.lang.*        -> ✅ lang3.*
❌ org.apache.commons.collections.* -> ✅ collections4.*
❌ com.alibaba.fastjson.*           -> ✅ fastjson2/jackson
❌ java.sql.Date/Time/Timestamp     -> ✅ java.time.*
❌ org.apache.logging.log4j.*       -> ✅ org.slf4j.*
❌ sun.misc.BASE64*                 -> ✅ java.util.Base64
```

**完全禁止**
```
❌ google.api.client.repackaged.*, avro.shaded.*
org.apache.hadoop.*.shaded.*, javax.ws.rs.ext.*
org.jetbrains.annotations.*, javafx.util.*, jline.internal.*
org.apache.commons.logging.*, org.codehaus.jackson.*
```

## 强制要求

**1. 空catch块**
```java
❌ catch (Exception e) {}
✅ catch (Exception e) { log.error("Error", e); }
✅ catch (Exception ignore) {}  // 变量名ignore/expected可以
```

**2. switch必须有default**
```java
✅ switch (type) { case A: break; default: break; }
```

**3. 重写equals必须重写hashCode**

**4. public类必须有Javadoc**
```java
✅ /** 用户服务类 */
   public class UserService {}
```

## 格式规范

```java
✅ if (a > 0) {           // 关键字后、运算符两侧有空格
✅ method(a, b)           // 逗号后有空格
✅ (String) obj           // 类型转换后有空格
✅ array[0]               // 方括号内无空格
✅ object.method()        // 点号左行靠 (换行时)
✅ LONG_NAME              // 常量大写
✅ log / logger           // 日志变量好小写
✅ UserService            // 类名大驼峰
✅ String[] args          // 数组声明
✅ 100L                   // 长整型大写L
✅ "yyyy-MM-dd"           // 日期格式小写y (非YYYY)
✅ 行尾无\s0, 4空格缩进，禁Tab
```

---

## 生成代码后强制检查

**Import检查 (5秒)**
1. 6大组顺序对？(静态/java/javax/org/com/其他)
2. 组间有空行？
3. 每个大组内部无空行？
4. 每组内部.分段逐段字母排序？
5. kuisbou.common在com组？(错，在其他组)

**代码检查 (5秒)**
1. 行尾无空格？
2. 无System.out？无禁用包？
3. switch有default？
4. equals配hashCode？public类有JavaDoc？
5. 日期小写yyyy？行长<150？无Tab？

-----

## __replace_in_file格式强检查

### 核心原则
**修改代码replace_in_file时必须格式预检查，防止引入格式问题**

### 预检清单
1. 行尾无空格/ Tab？
2. Import按6大组排序？
3. 组内无空行？
4. 注释格式正确？

### 执行流程
1. **内容读取** -> 验证替换内容格式
2. **样式检查** -> 确保SEARCH/REPLACE准确
3. **结果检查** -> 验证替换后格式正确

### 常见问题防御
```java
// 错误缩进
public void method() {
```
✅ // 行尾无空格
```java
public void method() {
```

**Import顺序错误**
```java
import com.kwai.zt.domain.config.A;
import com.kwai.threshold.B;
```
✅ // 排序修正
```java
import com.kwai.threshold.B;
import com.kwai.zt.domain.config.A;
```

### 强制规则
如果不符合上述 __replace_in_file格式检查
**禁止生成 write_to_file**

## 记忆口诀

**行尾空格是大敌，写完一行照检查**
**6组分大组，静态java javax org com其他**
**组间要空行，组内排序不要空**
**组内点分段，逐段比较字母序**
**threshold topic user util这t-h t-o u-s u-t**

## 错误TOP10

1. **行尾空格** (30%)
2. **大组缺空行** (20%)
3. **组内排序错误** (17%)
4. **组内多余空行** (15%)
5. **com组放入其它** (8%)
6. **System.out** (5%)
7. **No JavaDoc** (4%)
8. **禁用包** (3%)
9. **无default** (2%)
10. **Tab/Default/Tab** (1%)

## 生成代码前1秒自检

**[Import 5问]**
问题1: 6大组分好？组间有空行？
问题2: 每个大组内部无空行？
问题3: 每组内部.分段逐段字母排序？
问题4: threshold<topic, user<util?

**[代码 5问]**
问题1: 行尾无空格？行长<150? 无Tab?
问题2: 无System.out？无禁用包？
问题3: switch中default？
问题4: public类有JavaDoc？日期小写yyyy？

## 核心检查逻辑

```
**Import排序核心记忆:**

1. 6大组 + 组间空行
   静态导入 (空行) java (空行) javax (空行) org (空行) com (空行) 其他

2. 每个大组是一个整体：
   - 组内按.分段逐段字母排序
   - 组内无空行 (即使第一段变化)

   ✅ **静态导入组：**
      `import static com.kwai.xxx;`
      `import static java.util.xxx;`   // 无需空行
      `import static org.apache.xxx;`  // 无需空行
   
   ✅ **其他组：**
      `import eu.bitwalker.xxx;`
      `import io.netty.xxx;`           // 无需空行
      `import kuaishou.common.xxx;`    // 无需空行

3. 分段排序规则:
   - 按.分段逐段字母排序
   - threshold(t-h) < topic(t-o)
   - user(u-s) < util(u-t)

4. 每行结尾检查行尾无空格
```

---

## 强制提醒

**每次生成包含import的代码时，AI必须在脑海中执行：**
1. 分组算法 (6大组)
2. 排序算法 (字典序)
3. 空行算法 (组间空行)
4. 验证算法 (最终检查)

**如发现任何一步出错，立即重新执行整个流程！**

**核心：6大组间必空行，每个大组内部无空行，组内按.分段逐段字母排序**