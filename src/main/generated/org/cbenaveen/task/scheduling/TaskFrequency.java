/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package org.cbenaveen.task.scheduling;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class TaskFrequency extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -1333339780505244917L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TaskFrequency\",\"namespace\":\"org.cbenaveen.task.scheduling\",\"fields\":[{\"name\":\"frequencyTimeUnit\",\"type\":{\"type\":\"enum\",\"name\":\"TaskFrequencyTimeUnits\",\"symbols\":[\"MILLISECONDS\",\"SECONDS\",\"MINUTES\",\"HOURS\",\"DAYS\",\"WEEKS\"]}},{\"name\":\"time\",\"type\":\"int\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TaskFrequency> ENCODER =
      new BinaryMessageEncoder<TaskFrequency>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TaskFrequency> DECODER =
      new BinaryMessageDecoder<TaskFrequency>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<TaskFrequency> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<TaskFrequency> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TaskFrequency>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this TaskFrequency to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a TaskFrequency from a ByteBuffer. */
  public static TaskFrequency fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public org.cbenaveen.task.scheduling.TaskFrequencyTimeUnits frequencyTimeUnit;
  @Deprecated public int time;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TaskFrequency() {}

  /**
   * All-args constructor.
   * @param frequencyTimeUnit The new value for frequencyTimeUnit
   * @param time The new value for time
   */
  public TaskFrequency(org.cbenaveen.task.scheduling.TaskFrequencyTimeUnits frequencyTimeUnit, java.lang.Integer time) {
    this.frequencyTimeUnit = frequencyTimeUnit;
    this.time = time;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return frequencyTimeUnit;
    case 1: return time;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: frequencyTimeUnit = (org.cbenaveen.task.scheduling.TaskFrequencyTimeUnits)value$; break;
    case 1: time = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'frequencyTimeUnit' field.
   * @return The value of the 'frequencyTimeUnit' field.
   */
  public org.cbenaveen.task.scheduling.TaskFrequencyTimeUnits getFrequencyTimeUnit() {
    return frequencyTimeUnit;
  }

  /**
   * Sets the value of the 'frequencyTimeUnit' field.
   * @param value the value to set.
   */
  public void setFrequencyTimeUnit(org.cbenaveen.task.scheduling.TaskFrequencyTimeUnits value) {
    this.frequencyTimeUnit = value;
  }

  /**
   * Gets the value of the 'time' field.
   * @return The value of the 'time' field.
   */
  public java.lang.Integer getTime() {
    return time;
  }

  /**
   * Sets the value of the 'time' field.
   * @param value the value to set.
   */
  public void setTime(java.lang.Integer value) {
    this.time = value;
  }

  /**
   * Creates a new TaskFrequency RecordBuilder.
   * @return A new TaskFrequency RecordBuilder
   */
  public static org.cbenaveen.task.scheduling.TaskFrequency.Builder newBuilder() {
    return new org.cbenaveen.task.scheduling.TaskFrequency.Builder();
  }

  /**
   * Creates a new TaskFrequency RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TaskFrequency RecordBuilder
   */
  public static org.cbenaveen.task.scheduling.TaskFrequency.Builder newBuilder(org.cbenaveen.task.scheduling.TaskFrequency.Builder other) {
    return new org.cbenaveen.task.scheduling.TaskFrequency.Builder(other);
  }

  /**
   * Creates a new TaskFrequency RecordBuilder by copying an existing TaskFrequency instance.
   * @param other The existing instance to copy.
   * @return A new TaskFrequency RecordBuilder
   */
  public static org.cbenaveen.task.scheduling.TaskFrequency.Builder newBuilder(org.cbenaveen.task.scheduling.TaskFrequency other) {
    return new org.cbenaveen.task.scheduling.TaskFrequency.Builder(other);
  }

  /**
   * RecordBuilder for TaskFrequency instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TaskFrequency>
    implements org.apache.avro.data.RecordBuilder<TaskFrequency> {

    private org.cbenaveen.task.scheduling.TaskFrequencyTimeUnits frequencyTimeUnit;
    private int time;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(org.cbenaveen.task.scheduling.TaskFrequency.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.frequencyTimeUnit)) {
        this.frequencyTimeUnit = data().deepCopy(fields()[0].schema(), other.frequencyTimeUnit);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.time)) {
        this.time = data().deepCopy(fields()[1].schema(), other.time);
        fieldSetFlags()[1] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing TaskFrequency instance
     * @param other The existing instance to copy.
     */
    private Builder(org.cbenaveen.task.scheduling.TaskFrequency other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.frequencyTimeUnit)) {
        this.frequencyTimeUnit = data().deepCopy(fields()[0].schema(), other.frequencyTimeUnit);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.time)) {
        this.time = data().deepCopy(fields()[1].schema(), other.time);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'frequencyTimeUnit' field.
      * @return The value.
      */
    public org.cbenaveen.task.scheduling.TaskFrequencyTimeUnits getFrequencyTimeUnit() {
      return frequencyTimeUnit;
    }

    /**
      * Sets the value of the 'frequencyTimeUnit' field.
      * @param value The value of 'frequencyTimeUnit'.
      * @return This builder.
      */
    public org.cbenaveen.task.scheduling.TaskFrequency.Builder setFrequencyTimeUnit(org.cbenaveen.task.scheduling.TaskFrequencyTimeUnits value) {
      validate(fields()[0], value);
      this.frequencyTimeUnit = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'frequencyTimeUnit' field has been set.
      * @return True if the 'frequencyTimeUnit' field has been set, false otherwise.
      */
    public boolean hasFrequencyTimeUnit() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'frequencyTimeUnit' field.
      * @return This builder.
      */
    public org.cbenaveen.task.scheduling.TaskFrequency.Builder clearFrequencyTimeUnit() {
      frequencyTimeUnit = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'time' field.
      * @return The value.
      */
    public java.lang.Integer getTime() {
      return time;
    }

    /**
      * Sets the value of the 'time' field.
      * @param value The value of 'time'.
      * @return This builder.
      */
    public org.cbenaveen.task.scheduling.TaskFrequency.Builder setTime(int value) {
      validate(fields()[1], value);
      this.time = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'time' field has been set.
      * @return True if the 'time' field has been set, false otherwise.
      */
    public boolean hasTime() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'time' field.
      * @return This builder.
      */
    public org.cbenaveen.task.scheduling.TaskFrequency.Builder clearTime() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TaskFrequency build() {
      try {
        TaskFrequency record = new TaskFrequency();
        record.frequencyTimeUnit = fieldSetFlags()[0] ? this.frequencyTimeUnit : (org.cbenaveen.task.scheduling.TaskFrequencyTimeUnits) defaultValue(fields()[0]);
        record.time = fieldSetFlags()[1] ? this.time : (java.lang.Integer) defaultValue(fields()[1]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TaskFrequency>
    WRITER$ = (org.apache.avro.io.DatumWriter<TaskFrequency>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TaskFrequency>
    READER$ = (org.apache.avro.io.DatumReader<TaskFrequency>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}