/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloudera.titubate;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A structure for storing state kept during a test. This class is not
 * thread-safe.
 */
public class State {

  private final HashMap<String,Object> stateMap;

  public State() {
    stateMap = new HashMap<String,Object>();
  }

  public State(State original) {
    stateMap = new HashMap<String,Object>(original.stateMap);
  }

  /**
   * Sets a state object.
   *
   * @param key key for object
   * @param value object
   */
  public void set(String key, Object value) {
    stateMap.put(key, value);
  }

  /**
   * Removes a state object.
   *
   * @param key key for object
   */
  public void remove(String key) {
    stateMap.remove(key);
  }

  /**
   * Checks if this state has an object for the given key.
   *
   * @param key key for object
   * @return true if object is present, false if not
   */
  public boolean has(String key) {
    return stateMap.containsKey(key);
  }

  /**
   * Gets a state object.
   *
   * @param key key for object
   * @return value object
   */
  public Object get(String key) {
    return stateMap.get(key);
  }

  /**
   * Gets a state object as a string.
   *
   * @param key key for object
   * @return value as string
   * @throws ClassCastException if the value object is not a string
   */
  public String getString(String key) {
    return (String) stateMap.get(key);
  }

  /**
   * Gets a state object as an integer.
   *
   * @param key key for object
   * @return value as integer
   * @throws ClassCastException if the value object is not an integer
   */
  public Integer getInt(String key) {
    return (Integer) stateMap.get(key);
  }

  /**
   * Gets a state object as a long.
   *
   * @param key key for object
   * @return value as long
   * @throws ClassCastException if the value object is not a long
   */
  public Long getLong(String key) {
    return (Long) stateMap.get(key);
  }

  /**
   * Gets this state's data as a map. Changes to the returned map do not
   * reflect back into this state.
   *
   * @return map of state data
   */
  public Map<String,Object> toMap() {
    return new HashMap<String, Object>(stateMap);
  }

  public String dump() {
    return dump(stateMap);
  }
  private static final Charset UTF8 = Charset.forName("UTF-8");
  public static String dump(Map<String, ?> m) {
    StringBuilder sb = new StringBuilder("{");
    boolean rest = false;
    for (Map.Entry<String, ?> e : m.entrySet()) {
      String key = e.getKey();
      Object value = e.getValue();
      if (rest) {
        sb.append(",");
      } else {
        rest = true;
      }
      sb.append(key).append(" = |");
      if (value == null) {
        sb.append("null");
      } else if (value instanceof String || value instanceof Collection || value instanceof Number) {
        sb.append(value.toString());
      } else if (value instanceof byte[]) {
        sb.append(new String((byte[]) value, UTF8));
      } else {
        sb.append(value.getClass().getName()).append(" -> ").append(value.toString());
      }
      sb.append("|");
    }
    return sb.append("}").toString();
  }
}
