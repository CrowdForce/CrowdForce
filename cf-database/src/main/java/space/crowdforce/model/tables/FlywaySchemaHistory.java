/*
 * This file is generated by jOOQ.
 */
package space.crowdforce.model.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row10;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import space.crowdforce.model.Indexes;
import space.crowdforce.model.Keys;
import space.crowdforce.model.Public;
import space.crowdforce.model.tables.records.FlywaySchemaHistoryRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FlywaySchemaHistory extends TableImpl<FlywaySchemaHistoryRecord> {

    private static final long serialVersionUID = 24949208;

    /**
     * The reference instance of <code>public.flyway_schema_history</code>
     */
    public static final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = new FlywaySchemaHistory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FlywaySchemaHistoryRecord> getRecordType() {
        return FlywaySchemaHistoryRecord.class;
    }

    /**
     * The column <code>public.flyway_schema_history.installed_rank</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, Integer> INSTALLED_RANK = createField(DSL.name("installed_rank"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.flyway_schema_history.version</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, String> VERSION = createField(DSL.name("version"), org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>public.flyway_schema_history.description</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.VARCHAR(200).nullable(false), this, "");

    /**
     * The column <code>public.flyway_schema_history.type</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, String> TYPE = createField(DSL.name("type"), org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false), this, "");

    /**
     * The column <code>public.flyway_schema_history.script</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, String> SCRIPT = createField(DSL.name("script"), org.jooq.impl.SQLDataType.VARCHAR(1000).nullable(false), this, "");

    /**
     * The column <code>public.flyway_schema_history.checksum</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, Integer> CHECKSUM = createField(DSL.name("checksum"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.flyway_schema_history.installed_by</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, String> INSTALLED_BY = createField(DSL.name("installed_by"), org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>public.flyway_schema_history.installed_on</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, LocalDateTime> INSTALLED_ON = createField(DSL.name("installed_on"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>public.flyway_schema_history.execution_time</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, Integer> EXECUTION_TIME = createField(DSL.name("execution_time"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.flyway_schema_history.success</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, Boolean> SUCCESS = createField(DSL.name("success"), org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * Create a <code>public.flyway_schema_history</code> table reference
     */
    public FlywaySchemaHistory() {
        this(DSL.name("flyway_schema_history"), null);
    }

    /**
     * Create an aliased <code>public.flyway_schema_history</code> table reference
     */
    public FlywaySchemaHistory(String alias) {
        this(DSL.name(alias), FLYWAY_SCHEMA_HISTORY);
    }

    /**
     * Create an aliased <code>public.flyway_schema_history</code> table reference
     */
    public FlywaySchemaHistory(Name alias) {
        this(alias, FLYWAY_SCHEMA_HISTORY);
    }

    private FlywaySchemaHistory(Name alias, Table<FlywaySchemaHistoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private FlywaySchemaHistory(Name alias, Table<FlywaySchemaHistoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> FlywaySchemaHistory(Table<O> child, ForeignKey<O, FlywaySchemaHistoryRecord> key) {
        super(child, key, FLYWAY_SCHEMA_HISTORY);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.FLYWAY_SCHEMA_HISTORY_S_IDX);
    }

    @Override
    public UniqueKey<FlywaySchemaHistoryRecord> getPrimaryKey() {
        return Keys.FLYWAY_SCHEMA_HISTORY_PK;
    }

    @Override
    public List<UniqueKey<FlywaySchemaHistoryRecord>> getKeys() {
        return Arrays.<UniqueKey<FlywaySchemaHistoryRecord>>asList(Keys.FLYWAY_SCHEMA_HISTORY_PK);
    }

    @Override
    public FlywaySchemaHistory as(String alias) {
        return new FlywaySchemaHistory(DSL.name(alias), this);
    }

    @Override
    public FlywaySchemaHistory as(Name alias) {
        return new FlywaySchemaHistory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public FlywaySchemaHistory rename(String name) {
        return new FlywaySchemaHistory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FlywaySchemaHistory rename(Name name) {
        return new FlywaySchemaHistory(name, null);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<Integer, String, String, String, String, Integer, String, LocalDateTime, Integer, Boolean> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
}
