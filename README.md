# DL4jDemo
1、加载数据<br/>
2、对数据降噪<br/>
3、使用schema来定义数据的结构，那个字段是什么类型，支持分类特征（枚举可以考虑通过参考表来实现）<br/>
4、构建转换过程，移除不必要特征，对某个特定的分类变量进行那种类型的转换，可以转为integer/OneHot编码。<br/>
也可以添加新字段<br/>
追加字符串到指定列<br/>
用某一列的值替换另一列的值<br/>
用指定的值替换某一列的值<br/>
将字符串列转为时间列<br/>
使用新定义的顺序对列进行重新排序<br/>
按照某一列的字段值对数据进行过滤<br/>
对数字类型的列进行特定的运算<br/>
【二分类，深层神经网络】<br/>
5、定义神经网络配置，需要选择激活函数，输入层和输出，定义层数，可以加一个可视化展示神经网络的输入输出
初始化要指定初始权重，优化器和对应的学习率
每一层神经网络需要定义<br/>
神经网络层分两类，全连接层和输出层<br/>
全连接层（也叫隐藏层）需要定义【输入结点个数】，【输出结点个数】，【激活函数】和【丢弃率】<br/>
输出层需要定义【损失函数】，【输入结点个数】，【输出结点个数】，【激活函数】和【丢弃率】<br/>
【多分类，（多用于图片分类）CNN，卷积神经网络】<br/>
【卷积层，必要】
new ConvolutionLayer.Builder(11,11) 卷积核的大小为11*11,分别是高度和宽度
                             .nIn(channels)输入通道数
                             .nOut(96)输出通道数
                             .stride(1,1)卷积核的步幅。步幅表示卷积核在对输入数据进行卷积操作时沿着高度和宽度方向的移动步长。这里的步幅设置为 (1,1)，表示卷积核在水平和垂直方向上每次移动一个像素。
                             .activation(Activation.RELU)指定激活函数
                             .build()
【局部响应归一化层LRN可选，非必要】
.layer(1, new LocalResponseNormalization.Builder().name("lrn1").build())
LRN层的名称为lr1。
局部响应归一化（LRN）是一种在卷积神经网络中用于提高模型泛化能力和鲁棒性的技术。
它通过对局部神经元的响应进行归一化来抑制神经元间的竞争，有助于防止过拟合。
LRN 层通常在卷积层之后使用，但在实践中，LRN 逐渐被其他技术（如 Batch Normalization）取代或认为不再必要。
【子采样层】
.layer(new SubsamplingLayer.Builder(PoolingType.MAX)
                        .kernelSize(3,3)
                        .build())
指定子采样层的类型，核的大小
用途：在卷积层之后进行特征提取和降维。逐步减小特征图的空间，保留更重要的特征信息。
【全连接层】
 .layer(new DenseLayer.Builder()
                        .nOut(500)
                        .dist(new NormalDistribution(0.001, 0.005))
                        .activation(Activation.RELU)
                        .build())
全连接层是神经网络中最基本的一种层，每个神经元与上一层中的所有神经元相连。在这种情况下，全连接层的输出大小为 500，意味着该层有 500 个神经元。
                        
在全连接层的配置中，使用了正态分布（Normal Distribution）来初始化权重。具体地，采用了均值为 0.001，标准差为 0.005 的正态分布来初始化权重参数。这有助于在训练初始阶段使得权重参数具有适当的随机性，从而有助于网络的收敛和训练效果。

此外，全连接层的激活函数（Activation Function）选择了 ReLU（Rectified Linear Unit），这是一种常用的激活函数，具有非线性特性，能够帮助神经网络学习复杂的模式和特征。
【输出层】
.layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD) 损失函数
                        .nOut(numLabels) 输出数量
                        .activation(Activation.SOFTMAX) 激活函数
                        .build())

【输入类型】
.setInputType(InputType.convolutional(30,30,3))
设置了输入类型为卷积类型（Convolutional），输入尺寸为 30x30，通道数为 3
【反向传播类型】
.backpropType(BackpropType.Standard)
指定了反向传播类型为标准类型（Standard）

对于CNN类型，如果要处理文本，可以使用Word2Vec和Doc2Vec将文本转为向量，再使用CNN进行处理
可以考虑集成一下DL4j的UI来对训练过程进行展示<br/>
