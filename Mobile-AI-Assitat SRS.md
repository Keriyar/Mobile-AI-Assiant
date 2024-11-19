
Them：Mobile GPT Assitant with vioce


[Keriyar/Mobile-AI-Assitant: Mobile GPT Assitant with vioce](https://github.com/Keriyar/Mobile-AI-Assitant)
### 1. 项目概述
- **项目名称**：AI Vioce Assitant
- **项目背景**：构建一个带有语音功能的AI助手，支持多种模型（API)，Multi-modal（txt/Image)
- **目标用户**：~~Mobile VPN sometime is difficult and unstable,but  domestic shit AI assitant is unsufferable~~

### 2. 功能需求
- **功能列表**：
	- 功能1：用户自主选择模型（GPT-4o，GPT-3.5，Claude）
		- 根据模型的不同选择对应的API
	- 功能2：实现模型API的接入、转化、显示
		  - 将接收到的API（JSON格式）转换为可显示的文本/图片
	- 功能3：Txt/Image的输入与显示
		- 将用户的输入的Txt/Image完整的显示在APP中，同时向API发送请求
	- 功能4：文本的识别与语音转换
		- 根据阿里云语音配置，将文本转换为语音输出
	- 功能5：历史记录的显示与选取
		- 显示用户过去的历史记录，并可以跳转至对应的会话
		- 历史记录的Tile，根据第一句话自动生成（可自定义）
- **用例图**：


![image.png](https://keriyar-images.oss-cn-qingdao.aliyuncs.com/img/202410242114394.png)


![image.png](https://keriyar-images.oss-cn-qingdao.aliyuncs.com/img/202410242120997.png)

![image.png](https://keriyar-images.oss-cn-qingdao.aliyuncs.com/img/202410242121964.png)


- 功能实现：
	- 界面设计：（宋云旭）
		- 根据上图样例，设计一致的界面UI与逻辑顺序
		- 界面UI：图形的样式、文本的样式
		- 逻辑顺序：界面的跳转逻辑与顺序、图形/按钮对应的功能
	- 数据存储：（吴方浩）
		- 单个Chat显示：存储向模型发送的数据（txt/image）与模型响应回调的数据，以聊天框的形式显示出来
		- 选择历史Chat：存储历史交互记录，根据选择进行相应的选取调用
	- API接口调用处理：（胡锡宇）
		- 创建API：到Azure OpenAI 平台部署模型API。
		- 处理Json data： 根据选择的不同模型，处理用户发送的数据，将数据通过API接口发送Json。对模型响应的Json数据进行处理，形成（txt/image）。

### 3. 非功能需求

1. **性能需求**：
   - 应用在用户请求后3秒内做出响应。
   - 语音转换的处理时间不超过2秒。

2. **兼容性需求**：
   - 支持Android 8.0及以上操作系统。

3. **可用性**：
   - 提供直观的用户界面。
   - 根据用户角色提供个性化提示。

### 4. 技术需求

1. **开发工具和技术**：
   - 编程语言：Java/Kotlin（安卓应用）
   - 语音合成技术：阿里云语音识别API。

2. **架构设计**：
   - MVC架构模式分离表示层与业务逻辑层。
   - 微服务架构以支持可伸缩的API服务。

3. **外部接口**：
   - GPT-3.5、GPT-4 API（通过OpenAI API）及Claude的API（Anthropic平台）。
   - 阿里云语音服务API（提供语音识别与生成）。

### 5. 数据需求

1. **数据库设计**：
   - 用户资料表：包括用户ID、选择的模型、历史交互记录。
   - 配置表：储存用户语音设定和历史记录。

2. **数据存储需求**：
   - 用户数据存储在本地

### 6. 项目计划

1. **时间表**：
   - 开发阶段：1个月。
   - 测试与优化：1天。

2. **资源需求**：


[发现一个超级工具，免费获取Open AI的API key，包含GPT4和GPT3.5，轻松解锁海量顶尖AI工具 - 知乎](https://zhuanlan.zhihu.com/p/683550238)


[阿里云语音最新教程12-25_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1Ta41167kv/?spm_id_from=333.337.search-card.all.click&vd_source=4edff91f055c6a0c3cdd8c6d63c570f6)

[手把手教你如何调用Azure OpenAI API并使用GPT3.5/GPT4大模型 - 知乎](https://zhuanlan.zhihu.com/p/674880947)

