# TensorFlow Lite 模型生成

### 预备工作

##### 导入相关库


```python
import os

import numpy as np

import tensorflow as tf
assert tf.__version__.startswith('2')

from tflite_model_maker import model_spec
from tflite_model_maker import image_classifier
from tflite_model_maker.config import ExportFormat
from tflite_model_maker.config import QuantizationConfig
from tflite_model_maker.image_classifier import DataLoader

import matplotlib.pyplot as plt
```


### 模型训练

##### 获取数据


```python
image_path = tf.keras.utils.get_file(
      'flower_photos.tgz',
      'https://storage.googleapis.com/download.tensorflow.org/example_images/flower_photos.tgz',
      extract=True)
image_path = os.path.join(os.path.dirname(image_path), 'flower_photos')

```

##### 运行实例

**第一步：加载数据集，并将数据集分为训练数据和测试数据**


```python
data = DataLoader.from_folder(image_path)
train_data, test_data = data.split(0.9)
```

    INFO:tensorflow:Load image with size: 3670, num_label: 5, labels: daisy, dandelion, roses, sunflowers, tulips.
    

    2023-05-21 08:27:11.481109: W tensorflow/stream_executor/platform/default/dso_loader.cc:64] Could not load dynamic library 'libcuda.so.1'; dlerror: libcuda.so.1: cannot open shared object file: No such file or directory; LD_LIBRARY_PATH: /opt/conda/envs/tf/lib/python3.8/site-packages/cv2/../../lib64:
    2023-05-21 08:27:11.481150: W tensorflow/stream_executor/cuda/cuda_driver.cc:269] failed call to cuInit: UNKNOWN ERROR (303)
    2023-05-21 08:27:11.481173: I tensorflow/stream_executor/cuda/cuda_diagnostics.cc:156] kernel driver does not appear to be running on this host (codespaces-8a48fe): /proc/driver/nvidia/version does not exist
    2023-05-21 08:27:11.491563: I tensorflow/core/platform/cpu_feature_guard.cc:151] This TensorFlow binary is optimized with oneAPI Deep Neural Network Library (oneDNN) to use the following CPU instructions in performance-critical operations:  AVX2 AVX512F FMA
    To enable them in other operations, rebuild TensorFlow with the appropriate compiler flags.
    

**第二步：训练Tensorflow模型**


```python
model = image_classifier.create(train_data)
```

    INFO:tensorflow:Retraining the models...
    Model: "sequential"
    _________________________________________________________________
     Layer (type)                Output Shape              Param #   
    =================================================================
     hub_keras_layer_v1v2 (HubKe  (None, 1280)             3413024   
     rasLayerV1V2)                                                   
                                                                     
     dropout (Dropout)           (None, 1280)              0         
                                                                     
     dense (Dense)               (None, 5)                 6405      
                                                                     
    =================================================================
    Total params: 3,419,429
    Trainable params: 6,405
    Non-trainable params: 3,413,024
    _________________________________________________________________
    None
    Epoch 1/5
    

    2023-05-21 08:27:21.420982: W tensorflow/core/framework/cpu_allocator_impl.cc:82] Allocation of 19267584 exceeds 10% of free system memory.
    2023-05-21 08:27:21.481092: W tensorflow/core/framework/cpu_allocator_impl.cc:82] Allocation of 19267584 exceeds 10% of free system memory.
    2023-05-21 08:27:21.498338: W tensorflow/core/framework/cpu_allocator_impl.cc:82] Allocation of 51380224 exceeds 10% of free system memory.
    2023-05-21 08:27:21.537900: W tensorflow/core/framework/cpu_allocator_impl.cc:82] Allocation of 19267584 exceeds 10% of free system memory.
    2023-05-21 08:27:21.600921: W tensorflow/core/framework/cpu_allocator_impl.cc:82] Allocation of 19267584 exceeds 10% of free system memory.
    

    103/103 [==============================] - 62s 581ms/step - loss: 0.8529 - accuracy: 0.7782
    Epoch 2/5
    103/103 [==============================] - 58s 563ms/step - loss: 0.6544 - accuracy: 0.8990
    Epoch 3/5
    103/103 [==============================] - 59s 566ms/step - loss: 0.6190 - accuracy: 0.9169
    Epoch 4/5
    103/103 [==============================] - 57s 551ms/step - loss: 0.6010 - accuracy: 0.9284
    Epoch 5/5
    103/103 [==============================] - 58s 558ms/step - loss: 0.5847 - accuracy: 0.9305
    

**第三步：评估模型**


```python
loss,accuracy = model.evaluate(test_data)
```

    12/12 [==============================] - 9s 530ms/step - loss: 0.6234 - accuracy: 0.9210
    

**第四步：导出Tensorflow Lite模型**


```python
model.export(export_dir='.')

```

    2023-05-21 08:33:09.097138: W tensorflow/python/util/util.cc:368] Sets are not currently considered sequences, but this may change in the future, so consider avoiding using them.
    

    INFO:tensorflow:Assets written to: /tmp/tmppb9eltsl/assets
    

    INFO:tensorflow:Assets written to: /tmp/tmppb9eltsl/assets
    2023-05-21 08:33:13.661351: I tensorflow/core/grappler/devices.cc:66] Number of eligible GPUs (core count >= 8, compute capability >= 0.0): 0
    2023-05-21 08:33:13.661511: I tensorflow/core/grappler/clusters/single_machine.cc:358] Starting new session
    2023-05-21 08:33:13.733699: I tensorflow/core/grappler/optimizers/meta_optimizer.cc:1164] Optimization results for grappler item: graph_to_optimize
      function_optimizer: Graph size after: 913 nodes (656), 923 edges (664), time = 32.223ms.
      function_optimizer: function_optimizer did nothing. time = 0.01ms.
    
    /opt/conda/envs/tf/lib/python3.8/site-packages/tensorflow/lite/python/convert.py:746: UserWarning: Statistics for quantized inputs were expected, but not specified; continuing anyway.
      warnings.warn("Statistics for quantized inputs were expected, but not "
    2023-05-21 08:33:14.973819: W tensorflow/compiler/mlir/lite/python/tf_tfl_flatbuffer_helpers.cc:357] Ignored output_format.
    2023-05-21 08:33:14.973866: W tensorflow/compiler/mlir/lite/python/tf_tfl_flatbuffer_helpers.cc:360] Ignored drop_control_dependency.
    

    INFO:tensorflow:Label file is inside the TFLite model with metadata.
    

    fully_quantize: 0, inference_type: 6, input_inference_type: 3, output_inference_type: 3
    INFO:tensorflow:Label file is inside the TFLite model with metadata.
    

    INFO:tensorflow:Saving labels in /tmp/tmpw8mnvuud/labels.txt
    

    INFO:tensorflow:Saving labels in /tmp/tmpw8mnvuud/labels.txt
    

    INFO:tensorflow:TensorFlow Lite model exported successfully: ./model.tflite
    

    INFO:tensorflow:TensorFlow Lite model exported successfully: ./model.tflite
    
