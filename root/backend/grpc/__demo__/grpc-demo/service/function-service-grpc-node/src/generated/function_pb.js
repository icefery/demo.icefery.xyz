// source: function.proto
/**
 * @fileoverview
 * @enhanceable
 * @suppress {missingRequire} reports error on implicit type usages.
 * @suppress {messageConventions} JS Compiler reports an error if a variable or
 *     field starts with 'MSG_' and isn't a translatable message.
 * @public
 */
// GENERATED CODE -- DO NOT EDIT!
/* eslint-disable */
// @ts-nocheck

var jspb = require('google-protobuf')
var goog = jspb
var global = function () {
  if (this) {
    return this
  }
  if (typeof window !== 'undefined') {
    return window
  }
  if (typeof global !== 'undefined') {
    return global
  }
  if (typeof self !== 'undefined') {
    return self
  }
  return Function('return this')()
}.call(null)

goog.exportSymbol('proto.FetchRequest', null, global)
goog.exportSymbol('proto.FetchResponse', null, global)
goog.exportSymbol('proto.Instance', null, global)
goog.exportSymbol('proto.Invocation', null, global)
goog.exportSymbol('proto.InvokeRequest', null, global)
goog.exportSymbol('proto.InvokeResponse', null, global)
/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.Instance = function (opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null)
}
goog.inherits(proto.Instance, jspb.Message)
if (goog.DEBUG && !COMPILED) {
  /**
   * @public
   * @override
   */
  proto.Instance.displayName = 'proto.Instance'
}
/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.Invocation = function (opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, proto.Invocation.repeatedFields_, null)
}
goog.inherits(proto.Invocation, jspb.Message)
if (goog.DEBUG && !COMPILED) {
  /**
   * @public
   * @override
   */
  proto.Invocation.displayName = 'proto.Invocation'
}
/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.FetchRequest = function (opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null)
}
goog.inherits(proto.FetchRequest, jspb.Message)
if (goog.DEBUG && !COMPILED) {
  /**
   * @public
   * @override
   */
  proto.FetchRequest.displayName = 'proto.FetchRequest'
}
/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.FetchResponse = function (opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, proto.FetchResponse.repeatedFields_, null)
}
goog.inherits(proto.FetchResponse, jspb.Message)
if (goog.DEBUG && !COMPILED) {
  /**
   * @public
   * @override
   */
  proto.FetchResponse.displayName = 'proto.FetchResponse'
}
/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.InvokeRequest = function (opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, proto.InvokeRequest.repeatedFields_, null)
}
goog.inherits(proto.InvokeRequest, jspb.Message)
if (goog.DEBUG && !COMPILED) {
  /**
   * @public
   * @override
   */
  proto.InvokeRequest.displayName = 'proto.InvokeRequest'
}
/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.InvokeResponse = function (opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null)
}
goog.inherits(proto.InvokeResponse, jspb.Message)
if (goog.DEBUG && !COMPILED) {
  /**
   * @public
   * @override
   */
  proto.InvokeResponse.displayName = 'proto.InvokeResponse'
}

if (jspb.Message.GENERATE_TO_OBJECT) {
  /**
   * Creates an object representation of this proto.
   * Field names that are reserved in JavaScript and will be renamed to pb_name.
   * Optional fields that are not set will be set to undefined.
   * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
   * For the list of reserved names please see:
   *     net/proto2/compiler/js/internal/generator.cc#kKeyword.
   * @param {boolean=} opt_includeInstance Deprecated. whether to include the
   *     JSPB instance for transitional soy proto support:
   *     http://goto/soy-param-migration
   * @return {!Object}
   */
  proto.Instance.prototype.toObject = function (opt_includeInstance) {
    return proto.Instance.toObject(opt_includeInstance, this)
  }

  /**
   * Static version of the {@see toObject} method.
   * @param {boolean|undefined} includeInstance Deprecated. Whether to include
   *     the JSPB instance for transitional soy proto support:
   *     http://goto/soy-param-migration
   * @param {!proto.Instance} msg The msg instance to transform.
   * @return {!Object}
   * @suppress {unusedLocalVariables} f is only used for nested messages
   */
  proto.Instance.toObject = function (includeInstance, msg) {
    var f,
      obj = {
        name: jspb.Message.getFieldWithDefault(msg, 1, ''),
        addr: jspb.Message.getFieldWithDefault(msg, 2, ''),
        platform: jspb.Message.getFieldWithDefault(msg, 3, ''),
        language: jspb.Message.getFieldWithDefault(msg, 4, ''),
        protocol: jspb.Message.getFieldWithDefault(msg, 5, '')
      }

    if (includeInstance) {
      obj.$jspbMessageInstance = msg
    }
    return obj
  }
}

/**
 * Deserializes binary data (in protobuf wire format).
 * @param {jspb.ByteSource} bytes The bytes to deserialize.
 * @return {!proto.Instance}
 */
proto.Instance.deserializeBinary = function (bytes) {
  var reader = new jspb.BinaryReader(bytes)
  var msg = new proto.Instance()
  return proto.Instance.deserializeBinaryFromReader(msg, reader)
}

/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.Instance} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.Instance}
 */
proto.Instance.deserializeBinaryFromReader = function (msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break
    }
    var field = reader.getFieldNumber()
    switch (field) {
      case 1:
        var value = /** @type {string} */ (reader.readString())
        msg.setName(value)
        break
      case 2:
        var value = /** @type {string} */ (reader.readString())
        msg.setAddr(value)
        break
      case 3:
        var value = /** @type {string} */ (reader.readString())
        msg.setPlatform(value)
        break
      case 4:
        var value = /** @type {string} */ (reader.readString())
        msg.setLanguage(value)
        break
      case 5:
        var value = /** @type {string} */ (reader.readString())
        msg.setProtocol(value)
        break
      default:
        reader.skipField()
        break
    }
  }
  return msg
}

/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.Instance.prototype.serializeBinary = function () {
  var writer = new jspb.BinaryWriter()
  proto.Instance.serializeBinaryToWriter(this, writer)
  return writer.getResultBuffer()
}

/**
 * Serializes the given message to binary data (in protobuf wire
 * format), writing to the given BinaryWriter.
 * @param {!proto.Instance} message
 * @param {!jspb.BinaryWriter} writer
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.Instance.serializeBinaryToWriter = function (message, writer) {
  var f = undefined
  f = message.getName()
  if (f.length > 0) {
    writer.writeString(1, f)
  }
  f = message.getAddr()
  if (f.length > 0) {
    writer.writeString(2, f)
  }
  f = message.getPlatform()
  if (f.length > 0) {
    writer.writeString(3, f)
  }
  f = message.getLanguage()
  if (f.length > 0) {
    writer.writeString(4, f)
  }
  f = message.getProtocol()
  if (f.length > 0) {
    writer.writeString(5, f)
  }
}

/**
 * optional string name = 1;
 * @return {string}
 */
proto.Instance.prototype.getName = function () {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 1, ''))
}

/**
 * @param {string} value
 * @return {!proto.Instance} returns this
 */
proto.Instance.prototype.setName = function (value) {
  return jspb.Message.setProto3StringField(this, 1, value)
}

/**
 * optional string addr = 2;
 * @return {string}
 */
proto.Instance.prototype.getAddr = function () {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 2, ''))
}

/**
 * @param {string} value
 * @return {!proto.Instance} returns this
 */
proto.Instance.prototype.setAddr = function (value) {
  return jspb.Message.setProto3StringField(this, 2, value)
}

/**
 * optional string platform = 3;
 * @return {string}
 */
proto.Instance.prototype.getPlatform = function () {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 3, ''))
}

/**
 * @param {string} value
 * @return {!proto.Instance} returns this
 */
proto.Instance.prototype.setPlatform = function (value) {
  return jspb.Message.setProto3StringField(this, 3, value)
}

/**
 * optional string language = 4;
 * @return {string}
 */
proto.Instance.prototype.getLanguage = function () {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 4, ''))
}

/**
 * @param {string} value
 * @return {!proto.Instance} returns this
 */
proto.Instance.prototype.setLanguage = function (value) {
  return jspb.Message.setProto3StringField(this, 4, value)
}

/**
 * optional string protocol = 5;
 * @return {string}
 */
proto.Instance.prototype.getProtocol = function () {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 5, ''))
}

/**
 * @param {string} value
 * @return {!proto.Instance} returns this
 */
proto.Instance.prototype.setProtocol = function (value) {
  return jspb.Message.setProto3StringField(this, 5, value)
}

/**
 * List of repeated fields within this message type.
 * @private {!Array<number>}
 * @const
 */
proto.Invocation.repeatedFields_ = [3]

if (jspb.Message.GENERATE_TO_OBJECT) {
  /**
   * Creates an object representation of this proto.
   * Field names that are reserved in JavaScript and will be renamed to pb_name.
   * Optional fields that are not set will be set to undefined.
   * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
   * For the list of reserved names please see:
   *     net/proto2/compiler/js/internal/generator.cc#kKeyword.
   * @param {boolean=} opt_includeInstance Deprecated. whether to include the
   *     JSPB instance for transitional soy proto support:
   *     http://goto/soy-param-migration
   * @return {!Object}
   */
  proto.Invocation.prototype.toObject = function (opt_includeInstance) {
    return proto.Invocation.toObject(opt_includeInstance, this)
  }

  /**
   * Static version of the {@see toObject} method.
   * @param {boolean|undefined} includeInstance Deprecated. Whether to include
   *     the JSPB instance for transitional soy proto support:
   *     http://goto/soy-param-migration
   * @param {!proto.Invocation} msg The msg instance to transform.
   * @return {!Object}
   * @suppress {unusedLocalVariables} f is only used for nested messages
   */
  proto.Invocation.toObject = function (includeInstance, msg) {
    var f,
      obj = {
        host: jspb.Message.getFieldWithDefault(msg, 1, ''),
        port: jspb.Message.getFieldWithDefault(msg, 2, 0),
        instancesList: jspb.Message.toObjectList(msg.getInstancesList(), proto.Instance.toObject, includeInstance)
      }

    if (includeInstance) {
      obj.$jspbMessageInstance = msg
    }
    return obj
  }
}

/**
 * Deserializes binary data (in protobuf wire format).
 * @param {jspb.ByteSource} bytes The bytes to deserialize.
 * @return {!proto.Invocation}
 */
proto.Invocation.deserializeBinary = function (bytes) {
  var reader = new jspb.BinaryReader(bytes)
  var msg = new proto.Invocation()
  return proto.Invocation.deserializeBinaryFromReader(msg, reader)
}

/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.Invocation} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.Invocation}
 */
proto.Invocation.deserializeBinaryFromReader = function (msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break
    }
    var field = reader.getFieldNumber()
    switch (field) {
      case 1:
        var value = /** @type {string} */ (reader.readString())
        msg.setHost(value)
        break
      case 2:
        var value = /** @type {number} */ (reader.readInt32())
        msg.setPort(value)
        break
      case 3:
        var value = new proto.Instance()
        reader.readMessage(value, proto.Instance.deserializeBinaryFromReader)
        msg.addInstances(value)
        break
      default:
        reader.skipField()
        break
    }
  }
  return msg
}

/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.Invocation.prototype.serializeBinary = function () {
  var writer = new jspb.BinaryWriter()
  proto.Invocation.serializeBinaryToWriter(this, writer)
  return writer.getResultBuffer()
}

/**
 * Serializes the given message to binary data (in protobuf wire
 * format), writing to the given BinaryWriter.
 * @param {!proto.Invocation} message
 * @param {!jspb.BinaryWriter} writer
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.Invocation.serializeBinaryToWriter = function (message, writer) {
  var f = undefined
  f = message.getHost()
  if (f.length > 0) {
    writer.writeString(1, f)
  }
  f = message.getPort()
  if (f !== 0) {
    writer.writeInt32(2, f)
  }
  f = message.getInstancesList()
  if (f.length > 0) {
    writer.writeRepeatedMessage(3, f, proto.Instance.serializeBinaryToWriter)
  }
}

/**
 * optional string host = 1;
 * @return {string}
 */
proto.Invocation.prototype.getHost = function () {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 1, ''))
}

/**
 * @param {string} value
 * @return {!proto.Invocation} returns this
 */
proto.Invocation.prototype.setHost = function (value) {
  return jspb.Message.setProto3StringField(this, 1, value)
}

/**
 * optional int32 port = 2;
 * @return {number}
 */
proto.Invocation.prototype.getPort = function () {
  return /** @type {number} */ (jspb.Message.getFieldWithDefault(this, 2, 0))
}

/**
 * @param {number} value
 * @return {!proto.Invocation} returns this
 */
proto.Invocation.prototype.setPort = function (value) {
  return jspb.Message.setProto3IntField(this, 2, value)
}

/**
 * repeated Instance instances = 3;
 * @return {!Array<!proto.Instance>}
 */
proto.Invocation.prototype.getInstancesList = function () {
  return /** @type{!Array<!proto.Instance>} */ (jspb.Message.getRepeatedWrapperField(this, proto.Instance, 3))
}

/**
 * @param {!Array<!proto.Instance>} value
 * @return {!proto.Invocation} returns this
 */
proto.Invocation.prototype.setInstancesList = function (value) {
  return jspb.Message.setRepeatedWrapperField(this, 3, value)
}

/**
 * @param {!proto.Instance=} opt_value
 * @param {number=} opt_index
 * @return {!proto.Instance}
 */
proto.Invocation.prototype.addInstances = function (opt_value, opt_index) {
  return jspb.Message.addToRepeatedWrapperField(this, 3, opt_value, proto.Instance, opt_index)
}

/**
 * Clears the list making it empty but non-null.
 * @return {!proto.Invocation} returns this
 */
proto.Invocation.prototype.clearInstancesList = function () {
  return this.setInstancesList([])
}

if (jspb.Message.GENERATE_TO_OBJECT) {
  /**
   * Creates an object representation of this proto.
   * Field names that are reserved in JavaScript and will be renamed to pb_name.
   * Optional fields that are not set will be set to undefined.
   * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
   * For the list of reserved names please see:
   *     net/proto2/compiler/js/internal/generator.cc#kKeyword.
   * @param {boolean=} opt_includeInstance Deprecated. whether to include the
   *     JSPB instance for transitional soy proto support:
   *     http://goto/soy-param-migration
   * @return {!Object}
   */
  proto.FetchRequest.prototype.toObject = function (opt_includeInstance) {
    return proto.FetchRequest.toObject(opt_includeInstance, this)
  }

  /**
   * Static version of the {@see toObject} method.
   * @param {boolean|undefined} includeInstance Deprecated. Whether to include
   *     the JSPB instance for transitional soy proto support:
   *     http://goto/soy-param-migration
   * @param {!proto.FetchRequest} msg The msg instance to transform.
   * @return {!Object}
   * @suppress {unusedLocalVariables} f is only used for nested messages
   */
  proto.FetchRequest.toObject = function (includeInstance, msg) {
    var f,
      obj = {}

    if (includeInstance) {
      obj.$jspbMessageInstance = msg
    }
    return obj
  }
}

/**
 * Deserializes binary data (in protobuf wire format).
 * @param {jspb.ByteSource} bytes The bytes to deserialize.
 * @return {!proto.FetchRequest}
 */
proto.FetchRequest.deserializeBinary = function (bytes) {
  var reader = new jspb.BinaryReader(bytes)
  var msg = new proto.FetchRequest()
  return proto.FetchRequest.deserializeBinaryFromReader(msg, reader)
}

/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.FetchRequest} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.FetchRequest}
 */
proto.FetchRequest.deserializeBinaryFromReader = function (msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break
    }
    var field = reader.getFieldNumber()
    switch (field) {
      default:
        reader.skipField()
        break
    }
  }
  return msg
}

/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.FetchRequest.prototype.serializeBinary = function () {
  var writer = new jspb.BinaryWriter()
  proto.FetchRequest.serializeBinaryToWriter(this, writer)
  return writer.getResultBuffer()
}

/**
 * Serializes the given message to binary data (in protobuf wire
 * format), writing to the given BinaryWriter.
 * @param {!proto.FetchRequest} message
 * @param {!jspb.BinaryWriter} writer
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.FetchRequest.serializeBinaryToWriter = function (message, writer) {
  var f = undefined
}

/**
 * List of repeated fields within this message type.
 * @private {!Array<number>}
 * @const
 */
proto.FetchResponse.repeatedFields_ = [1]

if (jspb.Message.GENERATE_TO_OBJECT) {
  /**
   * Creates an object representation of this proto.
   * Field names that are reserved in JavaScript and will be renamed to pb_name.
   * Optional fields that are not set will be set to undefined.
   * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
   * For the list of reserved names please see:
   *     net/proto2/compiler/js/internal/generator.cc#kKeyword.
   * @param {boolean=} opt_includeInstance Deprecated. whether to include the
   *     JSPB instance for transitional soy proto support:
   *     http://goto/soy-param-migration
   * @return {!Object}
   */
  proto.FetchResponse.prototype.toObject = function (opt_includeInstance) {
    return proto.FetchResponse.toObject(opt_includeInstance, this)
  }

  /**
   * Static version of the {@see toObject} method.
   * @param {boolean|undefined} includeInstance Deprecated. Whether to include
   *     the JSPB instance for transitional soy proto support:
   *     http://goto/soy-param-migration
   * @param {!proto.FetchResponse} msg The msg instance to transform.
   * @return {!Object}
   * @suppress {unusedLocalVariables} f is only used for nested messages
   */
  proto.FetchResponse.toObject = function (includeInstance, msg) {
    var f,
      obj = {
        instancesList: jspb.Message.toObjectList(msg.getInstancesList(), proto.Instance.toObject, includeInstance)
      }

    if (includeInstance) {
      obj.$jspbMessageInstance = msg
    }
    return obj
  }
}

/**
 * Deserializes binary data (in protobuf wire format).
 * @param {jspb.ByteSource} bytes The bytes to deserialize.
 * @return {!proto.FetchResponse}
 */
proto.FetchResponse.deserializeBinary = function (bytes) {
  var reader = new jspb.BinaryReader(bytes)
  var msg = new proto.FetchResponse()
  return proto.FetchResponse.deserializeBinaryFromReader(msg, reader)
}

/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.FetchResponse} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.FetchResponse}
 */
proto.FetchResponse.deserializeBinaryFromReader = function (msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break
    }
    var field = reader.getFieldNumber()
    switch (field) {
      case 1:
        var value = new proto.Instance()
        reader.readMessage(value, proto.Instance.deserializeBinaryFromReader)
        msg.addInstances(value)
        break
      default:
        reader.skipField()
        break
    }
  }
  return msg
}

/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.FetchResponse.prototype.serializeBinary = function () {
  var writer = new jspb.BinaryWriter()
  proto.FetchResponse.serializeBinaryToWriter(this, writer)
  return writer.getResultBuffer()
}

/**
 * Serializes the given message to binary data (in protobuf wire
 * format), writing to the given BinaryWriter.
 * @param {!proto.FetchResponse} message
 * @param {!jspb.BinaryWriter} writer
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.FetchResponse.serializeBinaryToWriter = function (message, writer) {
  var f = undefined
  f = message.getInstancesList()
  if (f.length > 0) {
    writer.writeRepeatedMessage(1, f, proto.Instance.serializeBinaryToWriter)
  }
}

/**
 * repeated Instance instances = 1;
 * @return {!Array<!proto.Instance>}
 */
proto.FetchResponse.prototype.getInstancesList = function () {
  return /** @type{!Array<!proto.Instance>} */ (jspb.Message.getRepeatedWrapperField(this, proto.Instance, 1))
}

/**
 * @param {!Array<!proto.Instance>} value
 * @return {!proto.FetchResponse} returns this
 */
proto.FetchResponse.prototype.setInstancesList = function (value) {
  return jspb.Message.setRepeatedWrapperField(this, 1, value)
}

/**
 * @param {!proto.Instance=} opt_value
 * @param {number=} opt_index
 * @return {!proto.Instance}
 */
proto.FetchResponse.prototype.addInstances = function (opt_value, opt_index) {
  return jspb.Message.addToRepeatedWrapperField(this, 1, opt_value, proto.Instance, opt_index)
}

/**
 * Clears the list making it empty but non-null.
 * @return {!proto.FetchResponse} returns this
 */
proto.FetchResponse.prototype.clearInstancesList = function () {
  return this.setInstancesList([])
}

/**
 * List of repeated fields within this message type.
 * @private {!Array<number>}
 * @const
 */
proto.InvokeRequest.repeatedFields_ = [1]

if (jspb.Message.GENERATE_TO_OBJECT) {
  /**
   * Creates an object representation of this proto.
   * Field names that are reserved in JavaScript and will be renamed to pb_name.
   * Optional fields that are not set will be set to undefined.
   * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
   * For the list of reserved names please see:
   *     net/proto2/compiler/js/internal/generator.cc#kKeyword.
   * @param {boolean=} opt_includeInstance Deprecated. whether to include the
   *     JSPB instance for transitional soy proto support:
   *     http://goto/soy-param-migration
   * @return {!Object}
   */
  proto.InvokeRequest.prototype.toObject = function (opt_includeInstance) {
    return proto.InvokeRequest.toObject(opt_includeInstance, this)
  }

  /**
   * Static version of the {@see toObject} method.
   * @param {boolean|undefined} includeInstance Deprecated. Whether to include
   *     the JSPB instance for transitional soy proto support:
   *     http://goto/soy-param-migration
   * @param {!proto.InvokeRequest} msg The msg instance to transform.
   * @return {!Object}
   * @suppress {unusedLocalVariables} f is only used for nested messages
   */
  proto.InvokeRequest.toObject = function (includeInstance, msg) {
    var f,
      obj = {
        invocationsList: jspb.Message.toObjectList(msg.getInvocationsList(), proto.Invocation.toObject, includeInstance)
      }

    if (includeInstance) {
      obj.$jspbMessageInstance = msg
    }
    return obj
  }
}

/**
 * Deserializes binary data (in protobuf wire format).
 * @param {jspb.ByteSource} bytes The bytes to deserialize.
 * @return {!proto.InvokeRequest}
 */
proto.InvokeRequest.deserializeBinary = function (bytes) {
  var reader = new jspb.BinaryReader(bytes)
  var msg = new proto.InvokeRequest()
  return proto.InvokeRequest.deserializeBinaryFromReader(msg, reader)
}

/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.InvokeRequest} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.InvokeRequest}
 */
proto.InvokeRequest.deserializeBinaryFromReader = function (msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break
    }
    var field = reader.getFieldNumber()
    switch (field) {
      case 1:
        var value = new proto.Invocation()
        reader.readMessage(value, proto.Invocation.deserializeBinaryFromReader)
        msg.addInvocations(value)
        break
      default:
        reader.skipField()
        break
    }
  }
  return msg
}

/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.InvokeRequest.prototype.serializeBinary = function () {
  var writer = new jspb.BinaryWriter()
  proto.InvokeRequest.serializeBinaryToWriter(this, writer)
  return writer.getResultBuffer()
}

/**
 * Serializes the given message to binary data (in protobuf wire
 * format), writing to the given BinaryWriter.
 * @param {!proto.InvokeRequest} message
 * @param {!jspb.BinaryWriter} writer
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.InvokeRequest.serializeBinaryToWriter = function (message, writer) {
  var f = undefined
  f = message.getInvocationsList()
  if (f.length > 0) {
    writer.writeRepeatedMessage(1, f, proto.Invocation.serializeBinaryToWriter)
  }
}

/**
 * repeated Invocation invocations = 1;
 * @return {!Array<!proto.Invocation>}
 */
proto.InvokeRequest.prototype.getInvocationsList = function () {
  return /** @type{!Array<!proto.Invocation>} */ (jspb.Message.getRepeatedWrapperField(this, proto.Invocation, 1))
}

/**
 * @param {!Array<!proto.Invocation>} value
 * @return {!proto.InvokeRequest} returns this
 */
proto.InvokeRequest.prototype.setInvocationsList = function (value) {
  return jspb.Message.setRepeatedWrapperField(this, 1, value)
}

/**
 * @param {!proto.Invocation=} opt_value
 * @param {number=} opt_index
 * @return {!proto.Invocation}
 */
proto.InvokeRequest.prototype.addInvocations = function (opt_value, opt_index) {
  return jspb.Message.addToRepeatedWrapperField(this, 1, opt_value, proto.Invocation, opt_index)
}

/**
 * Clears the list making it empty but non-null.
 * @return {!proto.InvokeRequest} returns this
 */
proto.InvokeRequest.prototype.clearInvocationsList = function () {
  return this.setInvocationsList([])
}

if (jspb.Message.GENERATE_TO_OBJECT) {
  /**
   * Creates an object representation of this proto.
   * Field names that are reserved in JavaScript and will be renamed to pb_name.
   * Optional fields that are not set will be set to undefined.
   * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
   * For the list of reserved names please see:
   *     net/proto2/compiler/js/internal/generator.cc#kKeyword.
   * @param {boolean=} opt_includeInstance Deprecated. whether to include the
   *     JSPB instance for transitional soy proto support:
   *     http://goto/soy-param-migration
   * @return {!Object}
   */
  proto.InvokeResponse.prototype.toObject = function (opt_includeInstance) {
    return proto.InvokeResponse.toObject(opt_includeInstance, this)
  }

  /**
   * Static version of the {@see toObject} method.
   * @param {boolean|undefined} includeInstance Deprecated. Whether to include
   *     the JSPB instance for transitional soy proto support:
   *     http://goto/soy-param-migration
   * @param {!proto.InvokeResponse} msg The msg instance to transform.
   * @return {!Object}
   * @suppress {unusedLocalVariables} f is only used for nested messages
   */
  proto.InvokeResponse.toObject = function (includeInstance, msg) {
    var f,
      obj = {
        resultsMap: (f = msg.getResultsMap()) ? f.toObject(includeInstance, proto.Invocation.toObject) : []
      }

    if (includeInstance) {
      obj.$jspbMessageInstance = msg
    }
    return obj
  }
}

/**
 * Deserializes binary data (in protobuf wire format).
 * @param {jspb.ByteSource} bytes The bytes to deserialize.
 * @return {!proto.InvokeResponse}
 */
proto.InvokeResponse.deserializeBinary = function (bytes) {
  var reader = new jspb.BinaryReader(bytes)
  var msg = new proto.InvokeResponse()
  return proto.InvokeResponse.deserializeBinaryFromReader(msg, reader)
}

/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.InvokeResponse} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.InvokeResponse}
 */
proto.InvokeResponse.deserializeBinaryFromReader = function (msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break
    }
    var field = reader.getFieldNumber()
    switch (field) {
      case 1:
        var value = msg.getResultsMap()
        reader.readMessage(value, function (message, reader) {
          jspb.Map.deserializeBinary(
            message,
            reader,
            jspb.BinaryReader.prototype.readString,
            jspb.BinaryReader.prototype.readMessage,
            proto.Invocation.deserializeBinaryFromReader,
            '',
            new proto.Invocation()
          )
        })
        break
      default:
        reader.skipField()
        break
    }
  }
  return msg
}

/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.InvokeResponse.prototype.serializeBinary = function () {
  var writer = new jspb.BinaryWriter()
  proto.InvokeResponse.serializeBinaryToWriter(this, writer)
  return writer.getResultBuffer()
}

/**
 * Serializes the given message to binary data (in protobuf wire
 * format), writing to the given BinaryWriter.
 * @param {!proto.InvokeResponse} message
 * @param {!jspb.BinaryWriter} writer
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.InvokeResponse.serializeBinaryToWriter = function (message, writer) {
  var f = undefined
  f = message.getResultsMap(true)
  if (f && f.getLength() > 0) {
    f.serializeBinary(1, writer, jspb.BinaryWriter.prototype.writeString, jspb.BinaryWriter.prototype.writeMessage, proto.Invocation.serializeBinaryToWriter)
  }
}

/**
 * map<string, Invocation> results = 1;
 * @param {boolean=} opt_noLazyCreate Do not create the map if
 * empty, instead returning `undefined`
 * @return {!jspb.Map<string,!proto.Invocation>}
 */
proto.InvokeResponse.prototype.getResultsMap = function (opt_noLazyCreate) {
  return /** @type {!jspb.Map<string,!proto.Invocation>} */ (jspb.Message.getMapField(this, 1, opt_noLazyCreate, proto.Invocation))
}

/**
 * Clears values from the map. The map will be non-null.
 * @return {!proto.InvokeResponse} returns this
 */
proto.InvokeResponse.prototype.clearResultsMap = function () {
  this.getResultsMap().clear()
  return this
}

goog.object.extend(exports, proto)