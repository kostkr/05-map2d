package uj.wmii.pwj.map2d;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Interface providing 2-dimensional map.
 * It can be viewed as rows and cells sheet, with row keys and column keys.
 * Row and Column keys are using standard comparision mechanisms.
 *
 * @param <R> type of row keys
 * @param <C> type column keys
 * @param <V> type of values
 */
public interface Map2D<R, C, V> {

    /**
     * Puts a value to the map, at given row and columns keys.
     * If specified row-column key already contains element, it should be replaced.
     *
     * @param rowKey row part of the key.
     * @param columnKey column part of the key.
     * @param value object to put. It can be null.
     * @return object, that was previously contained by map within these coordinates, or {@code null} if it was empty.
     * @throws NullPointerException if rowKey or columnKey are {@code null}.
     */
    V put(R rowKey, C columnKey, V value);

    /**
     * Gets a value from the map from given key.
     *
     * @param rowKey row part of a key.
     * @param columnKey column part of a key.
     * @return object contained at specified key, or {@code null}, if the key does not contain an object.
     */
    V get(R rowKey, C columnKey);

    /**
     * Gets a value from the map from given key. If specified value does not exist, returns {@code defaultValue}.
     *
     * @param rowKey row part of a key.
     * @param columnKey column part of a key.
     * @param defaultValue value to be be returned, if specified key does not contain a value.
     * @return object contained at specified key, or {@code defaultValue}, if the key does not contain an object.
     */
    V getOrDefault(R rowKey, C columnKey, V defaultValue);

    /**
     * Removes a value from the map from given key.
     *
     * @param rowKey row part of a key.
     * @param columnKey column part of a key.
     * @return object contained at specified key, or {@code null}, if the key didn't contain an object.
     */
    V remove(R rowKey, C columnKey);

    /**
     * Checks if map contains no elements.
     * @return {@code true} if map doesn't contain any values; {@code false} otherwise.
     */
    boolean isEmpty();

    /**
     * Checks if map contains any element.
     * @return {@code true} if map contains at least one value; {@code false} otherwise.
     */
    boolean nonEmpty();

    /**
     * Return number of values being stored by this map.
     * @return number of values being stored
     */
    int size();

    /**
     * Removes all objects from a map.
     */
    void clear();

    /**
     * Returns a view of mappings for specified key.
     * Result map should be immutable. Later changes to this map should not affect result map.
     *
     * @param rowKey row key to get view map for.
     * @return map with view of particular row. If there is no values associated with specified row, empty map is returned.
     */
    Map<C, V> rowView(R rowKey);

    /**
     * Returns a view of mappings for specified column.
     * Result map should be immutable. Later changes to this map should not affect returned map.
     *
     * @param columnKey column key to get view map for.
     * @return map with view of particular column. If there is no values associated with specified column, empty map is returned.
     */
    Map<R, V> columnView(C columnKey);

    /**
     * Checks if map contains specified value.
     * @param value value to be checked
     * @return {@code true} if map contains specified value; {@code false} otherwise.
     */
    boolean hasValue(V value);

    /**
     * Checks if map contains a value under specified key.
     * @param rowKey row key to be checked
     * @param columnKey column key to be checked
     * @return {@code true} if map contains specified key; {@code false} otherwise.
     */
    boolean hasKey(R rowKey, C columnKey);

    /**
     * Checks if map contains at least one value within specified row.
     * @param rowKey row to be checked
     * @return {@code true} if map at least one value within specified row; {@code false} otherwise.
     */
    boolean hasRow(R rowKey);

    /**
     * Checks if map contains at least one value within specified column.
     * @param columnKey column to be checked
     * @return {@code true} if map at least one value within specified column; {@code false} otherwise.
     */
    boolean hasColumn(C columnKey);

    /**
     * Return a view of this map as map of rows to map of columns to values.
     * Result map should be immutable. Later changes to this map should not affect returned map.
     *
     * @return map with row-based view of this map. If this map is empty, empty map should be returned.
     */
    Map<R, Map<C,V>> rowMapView();

    /**
     * Return a view of this map as map of columns to map of rows to values.
     * Result map should be immutable. Later changes to this map should not affect returned map.
     *
     * @return map with column-based view of this map. If this map is empty, empty map should be returned.
     */
    Map<C, Map<R,V>> columnMapView();

    /**
     * Fills target map with all key-value maps from specified row.
     *
     * @param target map to be filled
     * @param rowKey row key to get data to fill map from
     * @return this map (floating)
     */
    Map2D<R, C, V> fillMapFromRow(Map<? super C, ? super V> target, R rowKey);

    /**
     * Fills target map with all key-value maps from specified row.
     *
     * @param target map to be filled
     * @param columnKey column key to get data to fill map from
     * @return this map (floating)
     */
    Map2D<R, C, V> fillMapFromColumn(Map<? super R, ? super V> target, C columnKey);

    /**
     * Puts all content of {@code source} map to this map.
     *
     * @param source map to make a copy from
     * @return this map (floating)
     */
    Map2D<R, C, V>  putAll(Map2D<? extends R, ? extends C, ? extends V> source);

    /**
     * Puts all content of {@code source} map to this map under specified row.
     * Ech key of {@code source} map becomes a column part of key.
     *
     * @param source map to make a copy from
     * @param rowKey object to use as row key
     * @return this map (floating)
     */
    Map2D<R, C, V>  putAllToRow(Map<? extends C, ? extends V> source, R rowKey);

    /**
     * Puts all content of {@code source} map to this map under specified column.
     * Ech key of {@code source} map becomes a row part of key.
     *
     * @param source map to make a copy from
     * @param columnKey object to use as column key
     * @return this map (floating)
     */
    Map2D<R, C, V>  putAllToColumn(Map<? extends R, ? extends V> source, C columnKey);

    /**
     * Creates a copy of this map, with application of conversions for rows, columns and values to specified types.
     * If as result of row or column convertion result key duplicates, then appriopriate row and / or column in target map has to be merged.
     * If merge ends up in key duplication, then it's up to specific implementation which value from possible to choose.
     *
     * @param rowFunction function converting row part of key
     * @param columnFunction function converting column part of key
     * @param valueFunction function converting value
     * @param <R2> result map row key type
     * @param <C2> result map column key type
     * @param <V2> result map value type
     * @return new instance of {@code Map2D} with converted objects
     */
    <R2, C2, V2> Map2D<R2, C2, V2> copyWithConversion(
        Function<? super R, ? extends R2> rowFunction,
        Function<? super C, ? extends C2> columnFunction,
        Function<? super V, ? extends V2> valueFunction);

    /**
     * Creates new instance of empty Map2D with default implementation.
     *
     * @param <R> row key type
     * @param <C> column key type
     * @param <V> value type
     * @return new instance of {@code Map2D}
     */
    static <R,C,V> Map2D<R,C,V> createInstance() {
        return new Map2D<>() {
            Map<R, Map<C, V>> map = new HashMap<>();
            @Override
            public V put(R rowKey, C columnKey, V value) {
                if( rowKey == null || columnKey == null )
                    throw new NullPointerException();

                map.computeIfAbsent(rowKey, k -> new HashMap<>());

                return map.get(rowKey).put(columnKey, value);
            }

            @Override
            public V get(R rowKey, C columnKey) {
                return map.get(rowKey) == null ? null : map.get(rowKey).get(columnKey);
            }

            @Override
            public V getOrDefault(R rowKey, C columnKey, V defaultValue) {
                V value = this.get(rowKey, columnKey);
                return value != null ? value : defaultValue;
            }

            @Override
            public V remove(R rowKey, C columnKey) {
                if(map.get(rowKey) != null){
                    if(map.get(rowKey).get(columnKey) != null)
                       return map.get(rowKey).remove(columnKey);
                }
                return null;
            }

            @Override
            public boolean isEmpty() {
                if(!map.isEmpty())
                    for (Map<C, V> columnMap : map.values()){
                        if(!columnMap.isEmpty()) return false;
                    }
                return true;
            }

            @Override
            public boolean nonEmpty() {
                return !map.isEmpty();
            }

            @Override
            public int size() {
                int size = 0;
                if(!map.isEmpty()){
                    for (Map<C, V> columnMap : map.values()){
                        size += columnMap.size();
                    }
                }
                return size;
            }

            @Override
            public void clear() {
                map.clear();
            }

            @Override
            public Map<C, V> rowView(R rowKey) {
                return new HashMap<>(map.get(rowKey));
            }

            @Override
            public Map<R, V> columnView(C columnKey) {
                Map<R, V> rowCopy = new HashMap<>();
                for (R row : map.keySet() ){
                    if (map.get(row).get(columnKey) != null)
                        rowCopy.put(row, map.get(row).get(columnKey));
                }
                return rowCopy;
            }

            @Override
            public boolean hasValue(V value) {
                for( Map<C, V> column : map.values() ){
                    if(column.containsValue(value))
                        return true;
                }
                return false;
            }

            @Override
            public boolean hasKey(R rowKey, C columnKey) {
                return this.get(rowKey, columnKey) != null;
            }

            @Override
            public boolean hasRow(R rowKey) {
                return map.containsKey(rowKey);
            }

            @Override
            public boolean hasColumn(C columnKey) {
                for(Map<C, V> columnMap : map.values() ){
                    if(columnMap.containsKey(columnKey))
                        return true;
                }
                return false;
            }

            @Override
            public Map<R, Map<C, V>> rowMapView() {
                Map<R, Map<C, V>> copyMap = new HashMap<>();
                for (R rowKey : map.keySet()){
                    copyMap.put(rowKey, this.rowView(rowKey));
                }
                return copyMap;
            }

            @Override
            public Map<C, Map<R, V>> columnMapView() {
                Map<C, Map<R, V>> copyMap = new HashMap<>();
                for (R row : map.keySet()){
                    for (C column : map.get(row).keySet()){
                        if (copyMap.containsKey(column))
                            copyMap.get(column).put(row, map.get(row).get(column));
                        else{
                            Map<R, V> tempC = new HashMap<>();
                            tempC.put(row, map.get(row).get(column));
                            copyMap.put(column, tempC);
                        }
                    }
                }
                return copyMap;
            }

            @Override
            public Map2D<R, C, V> fillMapFromRow(Map<? super C, ? super V> target, R rowKey) {
                if (map.containsKey(rowKey)) {
                    for (C columnKey : map.get(rowKey).keySet()) {
                        target.put(columnKey, map.get(rowKey).get(columnKey));
                    }
                }
                return this;
            }

            @Override
            public Map2D<R, C, V> fillMapFromColumn(Map<? super R, ? super V> target, C columnKey) {
                for (R rowKey : map.keySet()){
                    if (map.get(rowKey).containsKey(columnKey)){
                        target.put(rowKey, map.get(rowKey).get(columnKey));
                    }
                }
                return this;
            }

            @Override
            public Map2D<R, C, V> putAll(Map2D<? extends R, ? extends C, ? extends V> source) {
                Map<? extends R, ? extends Map<? extends C, ? extends V>> rowViewSource = source.rowMapView();
                for (R rowKey : rowViewSource.keySet()){
                    if(!map.containsKey(rowKey))
                        map.put(rowKey, new HashMap<>());

                    for (C columnKey: rowViewSource.get(rowKey).keySet()){
                        map.get(rowKey).put(columnKey, rowViewSource.get(rowKey).get(columnKey));
                    }
                }
                return this;
            }

            @Override
            public Map2D<R, C, V> putAllToRow(Map<? extends C, ? extends V> source, R rowKey) {
                if(!map.containsKey(rowKey))
                    map.put(rowKey, new HashMap<>());

                for (C columnKey: source.keySet()){
                    map.get(rowKey).put(columnKey, source.get(columnKey));
                }
                return this;
            }

            @Override
            public Map2D<R, C, V> putAllToColumn(Map<? extends R, ? extends V> source, C columnKey) {
                for(R rowKey : source.keySet()){
                    if (!map.containsKey(rowKey))
                        map.put(rowKey, new HashMap<>());
                    map.get(rowKey).put(columnKey, source.get(rowKey));
                }
                return this;
            }

            @Override
            public <R2, C2, V2> Map2D<R2, C2, V2> copyWithConversion(Function<? super R, ? extends R2> rowFunction, Function<? super C, ? extends C2> columnFunction, Function<? super V, ? extends V2> valueFunction) {
                Map2D<R2, C2, V2> result = Map2D.createInstance();
                for(R rowKey : map.keySet()){
                    for (C columnKey : map.get(rowKey).keySet()){
                        result.put(rowFunction.apply(rowKey), columnFunction.apply(columnKey), valueFunction.apply(map.get(rowKey).get(columnKey)));
                    }
                }
                return result;
            }
        };
    }
}
