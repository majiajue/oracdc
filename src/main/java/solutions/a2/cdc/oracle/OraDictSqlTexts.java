/**
 * Copyright (c) 2018-present, A2 Rešitve d.o.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package solutions.a2.cdc.oracle;

/**
 * 
 * @author averemee
 *
 */
public class OraDictSqlTexts {

	/*
select count(*)
from   ALL_MVIEW_LOGS L
where  (L.ROWIDS='YES' or L.PRIMARY_KEY='YES') and L.OBJECT_ID='NO'
  and  L.COMMIT_SCN_BASED='NO' and L.INCLUDE_NEW_VALUES='NO';
	 */
	public static final String MVIEW_COUNT_PK_SEQ_NOSCN_NONV_NOOI =
			"select count(*)\n" +
			"from   ALL_MVIEW_LOGS L\n" +
			"where  (L.ROWIDS='YES' or L.PRIMARY_KEY='YES') and L.OBJECT_ID='NO'\n" + 
			"  and  L.COMMIT_SCN_BASED='NO' and L.INCLUDE_NEW_VALUES='NO'\n";
	/*
select count(*)
from   ALL_MVIEW_LOGS L
where  (L.ROWIDS='YES' or L.PRIMARY_KEY='YES') and L.OBJECT_ID='NO'
  and  L.COMMIT_SCN_BASED='NO' and L.INCLUDE_NEW_VALUES='NO';
	 */
	public static final String MVIEW_COUNT_PK_SEQ_NOSCN_NONV_NOOI_PRE11G =
			"select count(*)\n" +
			"from   ALL_MVIEW_LOGS L\n" +
			"where  (L.ROWIDS='YES' or L.PRIMARY_KEY='YES') and L.OBJECT_ID='NO'\n" + 
			"  and L.INCLUDE_NEW_VALUES='NO'\n";

	/*
select L.LOG_OWNER, L.MASTER, L.LOG_TABLE, L.ROWIDS, L.PRIMARY_KEY
from   ALL_MVIEW_LOGS L
where  (L.ROWIDS='YES' or L.PRIMARY_KEY='YES') and L.OBJECT_ID='NO'
  and  L.COMMIT_SCN_BASED='NO' and L.INCLUDE_NEW_VALUES='NO';
	 */
	public static final String MVIEW_LIST_PK_SEQ_NOSCN_NONV_NOOI =
			"select L.LOG_OWNER, L.MASTER, L.LOG_TABLE, L.ROWIDS, L.PRIMARY_KEY, L.SEQUENCE\n" +
			"from   ALL_MVIEW_LOGS L\n" +
			"where  (L.ROWIDS='YES' or L.PRIMARY_KEY='YES') and L.OBJECT_ID='NO'\n" + 
			"  and  L.COMMIT_SCN_BASED='NO' and L.INCLUDE_NEW_VALUES='NO'\n";
	/*
select L.LOG_OWNER, L.MASTER, L.LOG_TABLE, L.ROWIDS, L.PRIMARY_KEY
from   ALL_MVIEW_LOGS L
where  (L.ROWIDS='YES' or L.PRIMARY_KEY='YES') and L.OBJECT_ID='NO'
  and  L.COMMIT_SCN_BASED='NO' and L.INCLUDE_NEW_VALUES='NO';
	 */
	public static final String MVIEW_LIST_PK_SEQ_NOSCN_NONV_NOOI_PRE11G =
			"select L.LOG_OWNER, L.MASTER, L.LOG_TABLE, L.ROWIDS, L.PRIMARY_KEY, L.SEQUENCE\n" +
			"from   ALL_MVIEW_LOGS L\n" +
			"where  (L.ROWIDS='YES' or L.PRIMARY_KEY='YES') and L.OBJECT_ID='NO'\n" + 
			"  and L.INCLUDE_NEW_VALUES='NO'\n";

	/*
select C.COLUMN_NAME, C.DATA_TYPE, C.DATA_LENGTH, C.DATA_PRECISION, C.DATA_SCALE,
       C.NULLABLE, C.COLUMN_ID, C.DATA_DEFAULT,
	(select 'Y'
	 from   ALL_TAB_COLUMNS TC
	 where  TC.TABLE_NAME='MLOG$_DEPT' and TC.OWNER=C.OWNER
	   and  TC.COLUMN_NAME=C.COLUMN_NAME and TC.COLUMN_NAME not like '%$$') PK
from   ALL_TAB_COLUMNS C
where  C.OWNER='SCOTT' and C.TABLE_NAME='DEPT'
  and    (C.DATA_TYPE in ('DATE', 'FLOAT', 'NUMBER', 'BINARY_FLOAT', 'BINARY_DOUBLE', 'RAW', 'CHAR', 'NCHAR', 'VARCHAR2', 'NVARCHAR2', 'BLOB', 'CLOB', 'NCLOB') or C.DATA_TYPE like 'TIMESTAMP%');
	 */
	public static final String COLUMN_LIST_MVIEW =
			"select C.COLUMN_NAME, C.DATA_TYPE, C.DATA_LENGTH, C.DATA_PRECISION, C.DATA_SCALE,\n" +
			"       C.NULLABLE, C.COLUMN_ID, C.DATA_DEFAULT,\n" +
			"	 (select 'Y'\n" + 
			"	  from   ALL_TAB_COLUMNS TC\n" +
			"	  where  TC.TABLE_NAME=? and TC.OWNER=C.OWNER\n" +
			"	    and  TC.COLUMN_NAME=C.COLUMN_NAME and TC.COLUMN_NAME not like '%$$') PK\n" +
			"from   ALL_TAB_COLUMNS C\n" +
			"where  C.OWNER=? and C.TABLE_NAME=?\n" +
			"  and  (C.DATA_TYPE in ('DATE', 'FLOAT', 'NUMBER', 'BINARY_FLOAT', 'BINARY_DOUBLE', 'RAW', 'CHAR', 'NCHAR', 'VARCHAR2', 'NVARCHAR2', 'BLOB', 'CLOB', 'NCLOB') or C.DATA_TYPE like 'TIMESTAMP%')";

	/* CDB_ views in Oracle does not support LONG data type required for DATA_DEFAULT column */
	/* Single SQL statement is used for non-CDB and CDB database but for CDB */
	/* alter session set CONTAINER=' ' must be executed before reading from ResultSet! */
	/*
select C.COLUMN_NAME, C.DATA_TYPE, C.DATA_LENGTH, C.DATA_PRECISION,
       C.DATA_SCALE, C.NULLABLE, C.COLUMN_ID, C.DATA_DEFAULT
from   DBA_TAB_COLS C
where  C.HIDDEN_COLUMN='NO'
  and  C.OWNER='SCOTT' and C.TABLE_NAME='EMP'
  and  (C.DATA_TYPE in ('DATE', 'FLOAT', 'NUMBER', 'BINARY_FLOAT', 'BINARY_DOUBLE', 'RAW', 'CHAR', 'NCHAR', 'VARCHAR2', 'NVARCHAR2', 'BLOB', 'CLOB', 'NCLOB')
       or C.DATA_TYPE like 'TIMESTAMP%' 
       or (C.DATA_TYPE='XMLTYPE' and C.DATA_TYPE_OWNER in ('SYS','PUBLIC')))
order by C.COLUMN_ID;
	 */
	public static final String COLUMN_LIST_PLAIN =
			"select C.COLUMN_NAME, C.DATA_TYPE, C.DATA_LENGTH, C.DATA_PRECISION,\n" +
			"       C.DATA_SCALE, C.NULLABLE, C.COLUMN_ID, C.DATA_DEFAULT\n" +
			"from   DBA_TAB_COLS C\n" +
			"where  C.HIDDEN_COLUMN='NO'\n" +
			"  and  C.OWNER=? and C.TABLE_NAME=?\n" +
			"  and  (C.DATA_TYPE in ('DATE', 'FLOAT', 'NUMBER', 'BINARY_FLOAT', 'BINARY_DOUBLE', 'RAW', 'CHAR', 'NCHAR', 'VARCHAR2', 'NVARCHAR2', 'BLOB', 'CLOB', 'NCLOB')\n" +
			"       or C.DATA_TYPE like 'TIMESTAMP%' \n" +
			"       or (C.DATA_TYPE='XMLTYPE' and C.DATA_TYPE_OWNER in ('SYS','PUBLIC')))\n" +
			"order by C.COLUMN_ID\n";

	/*
select C.COLUMN_NAME, L.SECUREFILE
from   DBA_LOBS L, DBA_OBJECTS O, DBA_TAB_COLS AC, DBA_TAB_COLS C
where  C.HIDDEN_COLUMN='NO'
  and   AC.OWNER=L.OWNER and AC.TABLE_NAME=L.TABLE_NAME and AC.COLUMN_NAME=L.COLUMN_NAME
  and   AC.OWNER=C.OWNER and AC.TABLE_NAME=C.TABLE_NAME and AC.COLUMN_ID=C.COLUMN_ID
  and   L.OWNER=O.OWNER and L.SEGMENT_NAME=O.OBJECT_NAME and O.OBJECT_ID=28031;
	 */
	public static final String MAP_DATAOBJ_TO_COLUMN_NON_CDB =
			"select C.COLUMN_NAME, L.SECUREFILE\n" +
			"from   DBA_LOBS L, DBA_OBJECTS O, DBA_TAB_COLS AC, DBA_TAB_COLS C\n" +
			"where  C.HIDDEN_COLUMN='NO'\n" +
			"  and  AC.OWNER=L.OWNER and AC.TABLE_NAME=L.TABLE_NAME and AC.COLUMN_NAME=L.COLUMN_NAME\n" +
			"  and  AC.OWNER=C.OWNER and AC.TABLE_NAME=C.TABLE_NAME and AC.COLUMN_ID=C.COLUMN_ID\n" +
			"  and  L.OWNER=O.OWNER and L.SEGMENT_NAME=O.OBJECT_NAME and O.OBJECT_ID=?\n";

	/*
select C.COLUMN_NAME, L.SECUREFILE
from   CDB_LOBS L, CDB_OBJECTS O, CDB_TAB_COLS AC, CDB_TAB_COLS C
where  C.HIDDEN_COLUMN='NO'
  and   AC.OWNER=L.OWNER and AC.TABLE_NAME=L.TABLE_NAME and AC.COLUMN_NAME=L.COLUMN_NAME
  and   AC.OWNER=C.OWNER and AC.TABLE_NAME=C.TABLE_NAME and AC.COLUMN_ID=C.COLUMN_ID
  and   L.OWNER=O.OWNER and L.SEGMENT_NAME=O.OBJECT_NAME and O.OBJECT_ID=28031;
	 */
	public static final String MAP_DATAOBJ_TO_COLUMN_CDB =
			"select C.COLUMN_NAME, L.SECUREFILE\n" +
			"from   CDB_LOBS L, CDB_OBJECTS O, CDB_TAB_COLS AC, CDB_TAB_COLS C\n" +
			"where  C.HIDDEN_COLUMN='NO'\n" +
			"  and  AC.OWNER=L.OWNER and AC.TABLE_NAME=L.TABLE_NAME and AC.COLUMN_NAME=L.COLUMN_NAME\n" +
			"  and  AC.OWNER=C.OWNER and AC.TABLE_NAME=C.TABLE_NAME and AC.COLUMN_ID=C.COLUMN_ID\n" +
			"  and  L.OWNER=O.OWNER and L.SEGMENT_NAME=O.OBJECT_NAME and O.OBJECT_ID=?\n";

	/*
select ALWAYS from DBA_LOG_GROUPS where OWNER='SCOTT' and TABLE_NAME='DEPT';
	 */
	public static final String SUPPLEMENTAL_LOGGING_NON_CDB =
			"select ALWAYS from DBA_LOG_GROUPS where OWNER=? and TABLE_NAME=?";

	/*
select ALWAYS from CDB_LOG_GROUPS where OWNER='SCOTT' and TABLE_NAME='DEPT' and CON_ID=0;
	 */
	public static final String SUPPLEMENTAL_LOGGING_CDB =
			"select ALWAYS from CDB_LOG_GROUPS where OWNER=? and TABLE_NAME=? and CON_ID=?";

	/*
select DCC.COLUMN_NAME
from   DBA_CONSTRAINTS C, DBA_CONS_COLUMNS DCC
where  C.OWNER=DCC.OWNER AND C.TABLE_NAME=DCC.TABLE_NAME and C.CONSTRAINT_NAME=DCC.CONSTRAINT_NAME and C.CONSTRAINT_TYPE='P'
  and  C.OWNER='SCOTT' and C.TABLE_NAME='DEPT';
	 */
	public static final String WELL_DEFINED_PK_COLUMNS_NON_CDB =
			"select DCC.COLUMN_NAME\n" +
			"from   DBA_CONSTRAINTS C, DBA_CONS_COLUMNS DCC\n" +
			"where  C.OWNER=DCC.OWNER AND C.TABLE_NAME=DCC.TABLE_NAME and C.CONSTRAINT_NAME=DCC.CONSTRAINT_NAME and C.CONSTRAINT_TYPE='P'\n" +
			"  and  C.OWNER=? and C.TABLE_NAME=?\n";

	/*
select DCC.COLUMN_NAME
from   CDB_CONSTRAINTS C, CDB_CONS_COLUMNS DCC
where  C.OWNER=DCC.OWNER AND C.TABLE_NAME=DCC.TABLE_NAME and C.CONSTRAINT_NAME=DCC.CONSTRAINT_NAME and C.CONSTRAINT_TYPE='P'
  and  C.CON_ID=DCC.CON_ID and C.OWNER='SCOTT' and C.TABLE_NAME='DEPT' and C.CON_ID=0;
	 */
	public static final String WELL_DEFINED_PK_COLUMNS_CDB =
			"select DCC.COLUMN_NAME\n" + 
			"from   CDB_CONSTRAINTS C, CDB_CONS_COLUMNS DCC\n" + 
			"where  C.OWNER=DCC.OWNER AND C.TABLE_NAME=DCC.TABLE_NAME and C.CONSTRAINT_NAME=DCC.CONSTRAINT_NAME and C.CONSTRAINT_TYPE='P'\n" + 
			"  and  C.CON_ID=DCC.CON_ID and C.OWNER=? and C.TABLE_NAME=? and C.CON_ID=?\n";

	/*
select TC.COLUMN_NAME
from   DBA_IND_COLUMNS IC, DBA_TAB_COLUMNS TC, (
	select RI.OWNER, RI.INDEX_NAME
	from (
    	select I.OWNER, I.INDEX_NAME, count(*) TOTAL, sum(case when TC.NULLABLE='N' then 1 else 0 end) NON_NULL
	    from   DBA_INDEXES I, DBA_IND_COLUMNS IC, DBA_TAB_COLUMNS TC
    	where  I.INDEX_TYPE='NORMAL' and I.UNIQUENESS='UNIQUE' and I.STATUS='VALID'
	      and  I.OWNER=IC.INDEX_OWNER and I.INDEX_NAME=IC.INDEX_NAME
    	  and  TC.OWNER=I.TABLE_OWNER and TC.TABLE_NAME=I.TABLE_NAME and IC.COLUMN_NAME=TC.COLUMN_NAME
	      and  I.TABLE_OWNER='INV' and I.TABLE_NAME='MTL_MATERIAL_TRANSACTIONS'
    	group by I.OWNER, I.INDEX_NAME
		order by TOTAL asc) RI
	where RI.TOTAL=RI.NON_NULL
	and rownum=1) FL
where TC.OWNER=IC.TABLE_OWNER and TC.TABLE_NAME=IC.TABLE_NAME and IC.COLUMN_NAME=TC.COLUMN_NAME
  and IC.INDEX_OWNER=FL.OWNER and IC.INDEX_NAME=FL.INDEX_NAME;
	 */
	public static final String LEGACY_DEFINED_PK_COLUMNS_NON_CDB =
			"select TC.COLUMN_NAME\n" + 
			"from   DBA_IND_COLUMNS IC, DBA_TAB_COLUMNS TC, (\n" + 
			"	select RI.OWNER, RI.INDEX_NAME\n" + 
			"	from (\n" + 
			"    	select I.OWNER, I.INDEX_NAME, count(*) TOTAL, sum(case when TC.NULLABLE='N' then 1 else 0 end) NON_NULL\n" + 
			"	    from   DBA_INDEXES I, DBA_IND_COLUMNS IC, DBA_TAB_COLUMNS TC\n" + 
			"    	where  I.INDEX_TYPE='NORMAL' and I.UNIQUENESS='UNIQUE' and I.STATUS='VALID'\n" + 
			"	      and  I.OWNER=IC.INDEX_OWNER and I.INDEX_NAME=IC.INDEX_NAME\n" + 
			"    	  and  TC.OWNER=I.TABLE_OWNER and TC.TABLE_NAME=I.TABLE_NAME and IC.COLUMN_NAME=TC.COLUMN_NAME\n" + 
			"	      and  I.TABLE_OWNER=? and I.TABLE_NAME=?\n" + 
			"    	group by I.OWNER, I.INDEX_NAME\n" + 
			"		order by TOTAL asc) RI\n" + 
			"	where RI.TOTAL=RI.NON_NULL\n" + 
			"	and rownum=1) FL\n" + 
			"where TC.OWNER=IC.TABLE_OWNER and TC.TABLE_NAME=IC.TABLE_NAME and IC.COLUMN_NAME=TC.COLUMN_NAME\n" + 
			"  and IC.INDEX_OWNER=FL.OWNER and IC.INDEX_NAME=FL.INDEX_NAME";
	/*
select TC.COLUMN_NAME
from   CDB_IND_COLUMNS IC, CDB_TAB_COLUMNS TC, (
	select RI.OWNER, RI.INDEX_NAME, RI.CON_ID
	from (
    	select I.OWNER, I.INDEX_NAME, I.CON_ID, count(*) TOTAL, sum(case when TC.NULLABLE='N' then 1 else 0 end) NON_NULL
	    from   CDB_INDEXES I, CDB_IND_COLUMNS IC, CDB_TAB_COLUMNS TC
    	where  I.INDEX_TYPE='NORMAL' and I.UNIQUENESS='UNIQUE' and I.STATUS='VALID'
	      and  I.OWNER=IC.INDEX_OWNER and I.INDEX_NAME=IC.INDEX_NAME
	      and  I.CON_ID=IC.CON_ID and IC.CON_ID=TC.CON_ID
    	  and  TC.OWNER=I.TABLE_OWNER and TC.TABLE_NAME=I.TABLE_NAME and IC.COLUMN_NAME=TC.COLUMN_NAME
	      and  I.TABLE_OWNER='INV' and I.TABLE_NAME='MTL_MATERIAL_TRANSACTIONS' and I.CON_ID=0
    	group by I.OWNER, I.INDEX_NAME, I.CON_ID
		order by TOTAL asc) RI
	where RI.TOTAL=RI.NON_NULL
	and rownum=1) FL
where TC.OWNER=IC.TABLE_OWNER and TC.TABLE_NAME=IC.TABLE_NAME and IC.COLUMN_NAME=TC.COLUMN_NAME
  and IC.INDEX_OWNER=FL.OWNER and IC.INDEX_NAME=FL.INDEX_NAME and IC.CON_ID=TC.CON_ID and IC.CON_ID=FL.CON_ID;
	 */
	public static final String LEGACY_DEFINED_PK_COLUMNS_CDB =
			"select TC.COLUMN_NAME\n" + 
			"from   CDB_IND_COLUMNS IC, CDB_TAB_COLUMNS TC, (\n" + 
			"	select RI.OWNER, RI.INDEX_NAME, RI.CON_ID\n" + 
			"	from (\n" + 
			"    	select I.OWNER, I.INDEX_NAME, I.CON_ID, count(*) TOTAL, sum(case when TC.NULLABLE='N' then 1 else 0 end) NON_NULL\n" + 
			"	    from   CDB_INDEXES I, CDB_IND_COLUMNS IC, CDB_TAB_COLUMNS TC\n" + 
			"    	where  I.INDEX_TYPE='NORMAL' and I.UNIQUENESS='UNIQUE' and I.STATUS='VALID'\n" + 
			"	      and  I.OWNER=IC.INDEX_OWNER and I.INDEX_NAME=IC.INDEX_NAME\n" + 
			"	      and  I.CON_ID=IC.CON_ID and IC.CON_ID=TC.CON_ID\n" + 
			"    	  and  TC.OWNER=I.TABLE_OWNER and TC.TABLE_NAME=I.TABLE_NAME and IC.COLUMN_NAME=TC.COLUMN_NAME\n" + 
			"	      and  I.TABLE_OWNER=? and I.TABLE_NAME=? and I.CON_ID=?\n" + 
			"    	group by I.OWNER, I.INDEX_NAME, I.CON_ID\n" + 
			"		order by TOTAL asc) RI\n" + 
			"	where RI.TOTAL=RI.NON_NULL\n" + 
			"	and rownum=1) FL\n" + 
			"where TC.OWNER=IC.TABLE_OWNER and TC.TABLE_NAME=IC.TABLE_NAME and IC.COLUMN_NAME=TC.COLUMN_NAME\n" + 
			"  and IC.INDEX_OWNER=FL.OWNER and IC.INDEX_NAME=FL.INDEX_NAME and IC.CON_ID=TC.CON_ID and IC.CON_ID=FL.CON_ID";

	/*
select DBID, NAME, DB_UNIQUE_NAME, PLATFORM_NAME, SUPPLEMENTAL_LOG_DATA_MIN, SUPPLEMENTAL_LOG_DATA_ALL, 
       (select VALUE from V$NLS_PARAMETERS where PARAMETER = 'NLS_CHARACTERSET') NLS_CHARACTERSET,
       (select VALUE from V$NLS_PARAMETERS where PARAMETER = 'NLS_NCHAR_CHARACTERSET') NLS_NCHAR_CHARACTERSET
from   V$DATABASE;
	 */
	public static final String DB_INFO_PRE12C =
			"select DBID, NAME, DB_UNIQUE_NAME, PLATFORM_NAME, SUPPLEMENTAL_LOG_DATA_MIN, SUPPLEMENTAL_LOG_DATA_ALL,\n" + 
			"       (select VALUE from V$NLS_PARAMETERS where PARAMETER = 'NLS_CHARACTERSET') NLS_CHARACTERSET,\n" + 
			"       (select VALUE from V$NLS_PARAMETERS where PARAMETER = 'NLS_NCHAR_CHARACTERSET') NLS_NCHAR_CHARACTERSET\n" + 
			"from   V$DATABASE\n";

	/*
select DBID, NAME, DB_UNIQUE_NAME, PLATFORM_NAME, SUPPLEMENTAL_LOG_DATA_MIN, SUPPLEMENTAL_LOG_DATA_ALL,
       CDB, SYS_CONTEXT('USERENV','CON_NAME') CON_NAME,
       (select VALUE from V$NLS_PARAMETERS where PARAMETER = 'NLS_CHARACTERSET') NLS_CHARACTERSET,
       (select VALUE from V$NLS_PARAMETERS where PARAMETER = 'NLS_NCHAR_CHARACTERSET') NLS_NCHAR_CHARACTERSET
from   V$DATABASE;
	 */
	public static final String DB_CDB_PDB_INFO =
			"select DBID, NAME, DB_UNIQUE_NAME, PLATFORM_NAME, SUPPLEMENTAL_LOG_DATA_MIN, SUPPLEMENTAL_LOG_DATA_ALL,\n" +
			"       CDB, SYS_CONTEXT('USERENV','CON_NAME') CON_NAME,\n" +
			"       (select VALUE from V$NLS_PARAMETERS where PARAMETER = 'NLS_CHARACTERSET') NLS_CHARACTERSET,\n" +
			"       (select VALUE from V$NLS_PARAMETERS where PARAMETER = 'NLS_NCHAR_CHARACTERSET') NLS_NCHAR_CHARACTERSET\n" +
			"from   V$DATABASE";

	/*
select VERSION, INSTANCE_NUMBER, INSTANCE_NAME, HOST_NAME, THREAD#,
       (select nvl(CPU_CORE_COUNT_CURRENT, CPU_COUNT_CURRENT) from V$LICENSE) CPU_CORE_COUNT_CURRENT
from   V$INSTANCE;
	 */
	public static final String RDBMS_VERSION_AND_MORE =
			"select VERSION, INSTANCE_NUMBER, INSTANCE_NAME, HOST_NAME, THREAD#,\n" +
			"(select nvl(CPU_CORE_COUNT_CURRENT, CPU_COUNT_CURRENT) from V$LICENSE) CPU_CORE_COUNT_CURRENT\n" +
			"from   V$INSTANCE";

/*
select PRODUCT, VERSION_FULL
from   PRODUCT_COMPONENT_VERSION
where  (PRODUCT like '%Database%Edition%' or upper(PRODUCT) like '%ORACLE%DATABASE%') and rownum=1;
 */
	public static final String RDBMS_PRODUCT_VERSION =
			"select PRODUCT, VERSION_FULL\n" +
			"from   PRODUCT_COMPONENT_VERSION\n" +
			"where  (PRODUCT like '%Database%Edition%' or upper(PRODUCT) like '%ORACLE%DATABASE%') and rownum=1";
/*
select PRODUCT
from   PRODUCT_COMPONENT_VERSION
where  PRODUCT like '%Database%Edition%' and rownum=1;
 */
	public static final String RDBMS_PRODUCT_VERSION_PRE18_1 =
			"select PRODUCT\n" +
			"from   PRODUCT_COMPONENT_VERSION\n" +
			"where  upper(PRODUCT) like '%DATABASE%' and rownum=1";

/*
select OPEN_MODE, DBID from V$DATABASE;
 */
	public static final String RDBMS_OPEN_MODE =
			"select OPEN_MODE, DBID, DB_UNIQUE_NAME from V$DATABASE";

	/*
select min(FIRST_CHANGE#)
from   V$ARCHIVED_LOG
where  ARCHIVED='YES' and STANDBY_DEST='NO' and DELETED='NO'
  and  THREAD#=1;
	 */
	public static final String FIRST_AVAILABLE_SCN_IN_ARCHIVE =
			"select min(FIRST_CHANGE#)\n" +
			"from   V$ARCHIVED_LOG\n" +
			"where  ARCHIVED='YES' and STANDBY_DEST='NO' and DELETED='NO'\n" +
			"  and  THREAD#=?";


	/*
select   NAME, THREAD#, SEQUENCE#, FIRST_CHANGE#, NEXT_CHANGE#, BLOCKS*BLOCK_SIZE BYTES, FIRST_TIME, (SYSDATE-FIRST_TIME)*86400 ACTUAL_LAG_SECONDS
from     V$ARCHIVED_LOG
where    ARCHIVED='YES' and STANDBY_DEST='NO' and DELETED='NO' and (? >= FIRST_CHANGE# or ? <= NEXT_CHANGE#)
  and    SEQUENCE# >= 
          (select min(SEQUENCE#)
           from   V$ARCHIVED_LOG
           where  ARCHIVED='YES' and STANDBY_DEST='NO' and ? between FIRST_CHANGE# and NEXT_CHANGE# and THREAD#=?)
  and    THREAD#=?
order by SEQUENCE#;
	 */
	public static final String ARCHIVED_LOGS =
			"select   NAME, THREAD#, SEQUENCE#, FIRST_CHANGE#, NEXT_CHANGE#, BLOCKS*BLOCK_SIZE BYTES, FIRST_TIME, (SYSDATE-FIRST_TIME)*86400 ACTUAL_LAG_SECONDS\n" + 
			"from     V$ARCHIVED_LOG\n" + 
			"where    ARCHIVED='YES' and STANDBY_DEST='NO' and DELETED='NO' and (? >= FIRST_CHANGE# or ? <= NEXT_CHANGE#)\n" + 
			"  and    SEQUENCE# >= \n" +
			"          (select min(SEQUENCE#)\n" + 
			"           from   V$ARCHIVED_LOG\n" +
			"           where  ARCHIVED='YES' and STANDBY_DEST='NO' and ? between FIRST_CHANGE# and NEXT_CHANGE# and THREAD#=?)\n" +
			"  and    THREAD#=?\n" +
			"order by SEQUENCE#";

	/*
declare
  l_OPTION binary_integer; 
begin
  if (? = 0) then
    l_OPTION := DBMS_LOGMNR.NEW;
  else
    l_OPTION := DBMS_LOGMNR.ADDFILE;
  end if;
  DBMS_LOGMNR.ADD_LOGFILE(LOGFILENAME => ?, OPTIONS =>  l_OPTION);
end;
	 */
	public static final String ADD_ARCHIVED_LOG =
			"declare\n" + 
			"  l_OPTION binary_integer; \n" + 
			"begin\n" + 
			"  if (? = 0) then\n" + 
			"    l_OPTION := DBMS_LOGMNR.NEW;\n" + 
			"  else\n" + 
			"    l_OPTION := DBMS_LOGMNR.ADDFILE;\n" + 
			"  end if;\n" + 
			"  DBMS_LOGMNR.ADD_LOGFILE(LOGFILENAME => ?, OPTIONS =>  l_OPTION);\n" + 
			"end;\n";
	/*
begin
  DBMS_LOGMNR.START_LOGMNR(
    STARTSCN => ?,
	ENDSCN => ?,
	OPTIONS =>  
      DBMS_LOGMNR.SKIP_CORRUPTION +
      DBMS_LOGMNR.NO_SQL_DELIMITER +
      DBMS_LOGMNR.NO_ROWID_IN_STMT);
end;
	 */
	public static final String START_LOGMINER =
			"begin\n" + 
			"  DBMS_LOGMNR.START_LOGMNR(\n" + 
			"    STARTSCN => ?,\n" +
			"	 ENDSCN => ?,\n" +
			"	 OPTIONS =>  \n" +
			"      DBMS_LOGMNR.SKIP_CORRUPTION +\n" +
			"      DBMS_LOGMNR.NO_SQL_DELIMITER +\n" + 
			"      DBMS_LOGMNR.NO_ROWID_IN_STMT);\n" + 
			"end;\n";

	/*
begin
  DBMS_LOGMNR.END_LOGMNR;
end;
	 */
	public static final String STOP_LOGMINER =
			"begin\n" + 
			"  DBMS_LOGMNR.END_LOGMNR;\n" + 
			"end;\n";

/*
select SCN, TIMESTAMP, OPERATION_CODE, XID, RS_ID, SSN, CSF, ROW_ID, DATA_OBJ#, SQL_REDO
from   V$LOGMNR_CONTENTS
where  OPERATION_CODE in (1,2,3)
 */
	public static final String MINE_DATA_NON_CDB =
			"select SCN, TIMESTAMP, OPERATION_CODE, XID, RS_ID, SSN, CSF, ROW_ID, DATA_OBJ#, DATA_OBJD#, SQL_REDO\n" + 
			"from   V$LOGMNR_CONTENTS\n";
/*
select SCN, TIMESTAMP, OPERATION_CODE, XID, RS_ID, SSN, CSF, ROW_ID, DATA_OBJ#, SQL_REDO,
       SRC_CON_UID, (select CON_ID from V$CONTAINERS C where C.CON_UID = L.SRC_CON_UID) CON_ID
from   V$LOGMNR_CONTENTS L
 */
	public static final String MINE_DATA_CDB =
			"select SCN, TIMESTAMP, OPERATION_CODE, XID, RS_ID, SSN, CSF, ROW_ID, DATA_OBJ#, DATA_OBJD#, SQL_REDO,\n" +
			"       SRC_CON_UID, (select CON_ID from V$CONTAINERS C where C.CON_UID = L.SRC_CON_UID) CON_ID\n" +
			"from   V$LOGMNR_CONTENTS L\n";

/*
 0 - INTERNAL
 1 - INSERT
 2 - DELETE
 3 - UPDATE
 9 - SELECT_LOB_LOCATOR
10 - LOB_WRITE
68 - XML DOC BEGIN
70 - XML DOC WRITE
71 - XML DOC END
select SCN, RS_ID, OPERATION_CODE, CSF, SQL_REDO
from   V$LOGMNR_CONTENTS
where  ((OPERATION_CODE in (0,1,2,3,9,10,68,70) and DATA_OBJ#=?) or OPERATION_CODE in (7,36)) and XID=? and SCN>=?
 */
	public static final String MINE_LOB_NON_CDB =
			"select SCN, RS_ID, OPERATION_CODE, CSF, SQL_REDO\n" + 
			"from   V$LOGMNR_CONTENTS\n" +
			"where  ((OPERATION_CODE in (0,1,2,3,9,10,68,70) and DATA_OBJ#=?) or OPERATION_CODE in (7,36)) and XID=? and SCN>=?";

/*
select SCN, RS_ID, OPERATION_CODE, CSF, SQL_REDO
from   V$LOGMNR_CONTENTS
where  ((OPERATION_CODE in (0,1,2,3,9,10,68,70) and DATA_OBJ#=?) or OPERATION_CODE in (7,36)) and XID=? and SCN>=? and SRC_CON_ID=?
 */
	public static final String MINE_LOB_CDB =
			"select SCN, RS_ID, OPERATION_CODE, CSF, SQL_REDO\n" + 
			"from   V$LOGMNR_CONTENTS\n" +
			"where  ((OPERATION_CODE in (0,1,2,3,9,10,68,70) and DATA_OBJ#=?) or OPERATION_CODE in (7,36)) and XID=? and SCN>=? and SRC_CON_UID=?";

/*
select O.OBJECT_ID, T.OWNER, T.TABLE_NAME, T.DEPENDENCIES,
       decode(O.OBJECT_TYPE, 'TABLE', 'Y', 'N') IS_TABLE,
       decode(O.OBJECT_TYPE, 'TABLE', O.OBJECT_ID,
         (select PT.OBJECT_ID
          from   DBA_OBJECTS PT
          where  PT.OWNER=O.OWNER
            and  PT.OBJECT_NAME=O.OBJECT_NAME
            and  PT.OBJECT_TYPE='TABLE')) PARENT_OBJECT_ID
from   DBA_OBJECTS O, DBA_TABLES T
where  O.OBJECT_TYPE in ('TABLE', 'TABLE PARTITION', 'TABLE SUBPARTITION')
  and  O.TEMPORARY='N'
  and  O.OWNER not in ('SYS','SYSTEM','MGDSYS','OJVMSYS','AUDSYS','OUTLN','APPQOSSYS','DBSNMP','CTXSYS','ORDSYS','ORDPLUGINS','ORDDATA','MDSYS','OLAPSYS','GGSYS','XDB','GSMADMIN_INTERNAL','DBSFWUSER','LBACSYS','DVSYS','WMSYS')
  and  O.OWNER=T.OWNER
  and  O.OBJECT_NAME=T.TABLE_NAME
  and  O.OBJECT_ID=:B1;
 */
	public static final String CHECK_TABLE_NON_CDB =
			"select O.OBJECT_ID, T.OWNER, T.TABLE_NAME, T.DEPENDENCIES,\n" + 
			"       decode(O.OBJECT_TYPE, 'TABLE', 'Y', 'N') IS_TABLE,\n" + 
			"       decode(O.OBJECT_TYPE, 'TABLE', O.OBJECT_ID,\n" + 
			"         (select PT.OBJECT_ID\n" + 
			"          from   DBA_OBJECTS PT\n" + 
			"          where  PT.OWNER=O.OWNER\n" + 
			"            and  PT.OBJECT_NAME=O.OBJECT_NAME\n" + 
			"            and  PT.OBJECT_TYPE='TABLE')) PARENT_OBJECT_ID\n" + 
			"from   DBA_OBJECTS O, DBA_TABLES T\n" + 
			"where  O.OBJECT_TYPE in ('TABLE', 'TABLE PARTITION', 'TABLE SUBPARTITION')\n" + 
			"  and  O.TEMPORARY='N'\n" + 
			"  and  O.OWNER not in ('SYS','SYSTEM','MGDSYS','OJVMSYS','AUDSYS','OUTLN','APPQOSSYS','DBSNMP','CTXSYS','ORDSYS','ORDPLUGINS','ORDDATA','MDSYS','OLAPSYS','GGSYS','XDB','GSMADMIN_INTERNAL','DBSFWUSER','LBACSYS','DVSYS','WMSYS')\n" + 
			"  and  O.OWNER=T.OWNER\n" + 
			"  and  O.OBJECT_NAME=T.TABLE_NAME\n";
	public static final String CHECK_TABLE_NON_CDB_WHERE_PARAM =
			"  and  O.OBJECT_ID=?\n";

/*
select O.OBJECT_ID, O.CON_ID, T.OWNER, T.TABLE_NAME, T.DEPENDENCIES, P.PDB_NAME,
       decode(O.OBJECT_TYPE, 'TABLE', 'Y', 'N') IS_TABLE,
       decode(O.OBJECT_TYPE, 'TABLE', O.OBJECT_ID,
         (select PT.OBJECT_ID
          from   CDB_OBJECTS PT
          where  PT.OWNER=O.OWNER
            and  PT.OBJECT_NAME=O.OBJECT_NAME
            and  PT.CON_ID=O.CON_ID
            and  PT.OBJECT_TYPE='TABLE')) PARENT_OBJECT_ID
from   CDB_OBJECTS O, CDB_PDBS P, CDB_TABLES T
where  O.OBJECT_TYPE in ('TABLE', 'TABLE PARTITION', 'TABLE SUBPARTITION')
  and  O.TEMPORARY='N'
  and  O.OWNER not in ('SYS','SYSTEM','MGDSYS','OJVMSYS','AUDSYS','OUTLN','APPQOSSYS','DBSNMP','CTXSYS','ORDSYS','ORDPLUGINS','ORDDATA','MDSYS','OLAPSYS','GGSYS','XDB','GSMADMIN_INTERNAL','DBSFWUSER','LBACSYS','DVSYS','WMSYS')
  and  O.CON_ID=P.CON_ID (+)
  and  O.OWNER=T.OWNER
  and  O.OBJECT_NAME=T.TABLE_NAME
  and  O.OBJECT_ID=:B1
  and  O.CON_ID=:B2;
 */
	public static final String CHECK_TABLE_CDB =
		"select O.OBJECT_ID, O.CON_ID, T.OWNER, T.TABLE_NAME, T.DEPENDENCIES, P.PDB_NAME,\n" + 
		"       decode(O.OBJECT_TYPE, 'TABLE', 'Y', 'N') IS_TABLE,\n" + 
		"       decode(O.OBJECT_TYPE, 'TABLE', O.OBJECT_ID,\n" + 
		"         (select PT.OBJECT_ID\n" + 
		"          from   CDB_OBJECTS PT\n" + 
		"          where  PT.OWNER=O.OWNER\n" + 
		"            and  PT.OBJECT_NAME=O.OBJECT_NAME\n" + 
		"            and  PT.CON_ID=O.CON_ID\n" + 
		"            and  PT.OBJECT_TYPE='TABLE')) PARENT_OBJECT_ID\n" + 
		"from   CDB_OBJECTS O, CDB_PDBS P, CDB_TABLES T\n" + 
		"where  O.OBJECT_TYPE in ('TABLE', 'TABLE PARTITION', 'TABLE SUBPARTITION')\n" + 
		"  and  O.TEMPORARY='N'\n" + 
		"  and  O.OWNER not in ('SYS','SYSTEM','MGDSYS','OJVMSYS','AUDSYS','OUTLN','APPQOSSYS','DBSNMP','CTXSYS','ORDSYS','ORDPLUGINS','ORDDATA','MDSYS','OLAPSYS','GGSYS','XDB','GSMADMIN_INTERNAL','DBSFWUSER','LBACSYS','DVSYS','WMSYS')\n" + 
		"  and  O.CON_ID=P.CON_ID (+)\n" + 
		"  and  O.OWNER=T.OWNER\n" + 
		"  and  O.OBJECT_NAME=T.TABLE_NAME\n";
	public static final String CHECK_TABLE_CDB_WHERE_PARAM =
		"  and  O.OBJECT_ID=?\n" +
		"  and  O.CON_ID=?\n";

/*
select O.OBJECT_ID, T.OWNER, T.TABLE_NAME, T.DEPENDENCIES
from   DBA_OBJECTS O, DBA_TABLES T
where  O.OBJECT_TYPE='TABLE'
  and  O.TEMPORARY='N'
  and  O.OWNER not in ('SYS','SYSTEM','MGDSYS','OJVMSYS','AUDSYS','OUTLN','APPQOSSYS','DBSNMP','CTXSYS','ORDSYS','ORDPLUGINS','ORDDATA','MDSYS','OLAPSYS','GGSYS','XDB','GSMADMIN_INTERNAL','DBSFWUSER','LBACSYS','DVSYS','WMSYS')
  and  O.OWNER=T.OWNER
  and  O.OBJECT_NAME=T.TABLE_NAME
 */
	public static final String INITIAL_LOAD_LIST_NON_CDB =
		"select O.OBJECT_ID, T.OWNER, T.TABLE_NAME, T.DEPENDENCIES\n" +
		"from   DBA_OBJECTS O, DBA_TABLES T\n" +
		"where  O.OBJECT_TYPE='TABLE'\n" +
		"  and  O.TEMPORARY='N'\n" +
		"  and  O.OWNER not in ('SYS','SYSTEM','MGDSYS','OJVMSYS','AUDSYS','OUTLN','APPQOSSYS','DBSNMP','CTXSYS','ORDSYS','ORDPLUGINS','ORDDATA','MDSYS','OLAPSYS','GGSYS','XDB','GSMADMIN_INTERNAL','DBSFWUSER','LBACSYS','DVSYS','WMSYS')\n" +
		"  and  O.OWNER=T.OWNER\n" +
		"  and  O.OBJECT_NAME=T.TABLE_NAME";
/*
select O.OBJECT_ID, O.CON_ID, T.OWNER, T.TABLE_NAME, T.DEPENDENCIES, P.PDB_NAME
from   CDB_OBJECTS O, CDB_PDBS P, CDB_TABLES T
where  O.OBJECT_TYPE='TABLE'
  and  O.TEMPORARY='N'
  and  O.OWNER not in ('SYS','SYSTEM','MGDSYS','OJVMSYS','AUDSYS','OUTLN','APPQOSSYS','DBSNMP','CTXSYS','ORDSYS','ORDPLUGINS','ORDDATA','MDSYS','OLAPSYS','GGSYS','XDB','GSMADMIN_INTERNAL','DBSFWUSER','LBACSYS','DVSYS','WMSYS')
  and  O.CON_ID=P.CON_ID (+)
  and  O.OWNER=T.OWNER
  and  O.OBJECT_NAME=T.TABLE_NAME
 */
	public static final String INITIAL_LOAD_LIST_CDB =
		"select O.OBJECT_ID, O.CON_ID, T.OWNER, T.TABLE_NAME, T.DEPENDENCIES, P.PDB_NAME\n" +
		"from   CDB_OBJECTS O, CDB_PDBS P, CDB_TABLES T\n" +
		"where  O.OBJECT_TYPE='TABLE'\n" +
		"  and  O.TEMPORARY='N'\n" +
		"  and  O.OWNER not in ('SYS','SYSTEM','MGDSYS','OJVMSYS','AUDSYS','OUTLN','APPQOSSYS','DBSNMP','CTXSYS','ORDSYS','ORDPLUGINS','ORDDATA','MDSYS','OLAPSYS','GGSYS','XDB','GSMADMIN_INTERNAL','DBSFWUSER','LBACSYS','DVSYS','WMSYS')\n" +
		"  and  O.CON_ID=P.CON_ID (+)\n" +
		"  and  O.OWNER=T.OWNER\n" +
		"  and  O.OBJECT_NAME=T.TABLE_NAME";

/*
select I$.INSTANCE_NAME
from   V$ACTIVE_INSTANCES A$, GV$INSTANCE I$
where  A$.INST_NUMBER = I$.INSTANCE_NUMBER
 */
	public static final String RAC_INSTANCES = 
			"select I$.INSTANCE_NAME\n" +
			"from   V$ACTIVE_INSTANCES A$, GV$INSTANCE I$\n" +
			"where  A$.INST_NUMBER = I$.INSTANCE_NUMBER";

	/*
select decode(OBJECT_TYPE, 'LOB', 1, 0) IS_LOB
from   DBA_OBJECTS
where  OBJECT_ID = ?
	 */
	public static final String LOB_CHECK_NON_CDB =
			"select decode(OBJECT_TYPE, 'LOB', 1, 0) IS_LOB\n" +
			"from   DBA_OBJECTS\n" +
			"where  OBJECT_ID = ?\n";

	/*
select decode(OBJECT_TYPE, 'LOB', 1, 0) IS_LOB
from   CDB_OBJECTS
where  OBJECT_ID = ? and CON_ID = ?
	 */
	public static final String LOB_CHECK_CDB =
			"select decode(OBJECT_TYPE, 'LOB', 1, 0) IS_LOB\n" +
			"from   CDB_OBJECTS\n" +
			"where  OBJECT_ID = ? and CON_ID = ?\n";

	/*
select THREAD#
from   V$THREAD
where  STATUS = 'OPEN' and ENABLED = 'PUBLIC'
	 */
	public static final String DG4RAC_THREADS =
			"select THREAD#\n" +
			"from   V$THREAD\n" +
			"where  STATUS = 'OPEN' and ENABLED = 'PUBLIC'\n";

	}
