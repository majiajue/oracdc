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

package eu.solutions.a2.cdc.oracle;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.solutions.a2.cdc.oracle.utils.OraSqlUtils;

public class OraCdcAlterTablePreProcessorTest {

	@Test
	public void test() {

		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("alter table dept rename column DESCRIPTION to COMMENTARY"),
				OraSqlUtils.ALTER_TABLE_COLUMN_RENAME + "\n" +
					"DESCRIPTION;COMMENTARY");
		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("alter table SCOTT.DEPT rename column DESCRIPTION to COMMENTARY"),
				OraSqlUtils.ALTER_TABLE_COLUMN_RENAME + "\n" +
					"DESCRIPTION;COMMENTARY");
		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("alter table scott. DEPT rename column DESCRIPTION to COMMENTARY"),
				OraSqlUtils.ALTER_TABLE_COLUMN_RENAME + "\n" +
					"DESCRIPTION;COMMENTARY");
		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("alter table SCOTT .dept rename column DESCRIPTION to COMMENTARY"),
				OraSqlUtils.ALTER_TABLE_COLUMN_RENAME + "\n" +
					"DESCRIPTION;COMMENTARY");
		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("alter table scott . dept rename column DESCRIPTION to COMMENTARY"),
				OraSqlUtils.ALTER_TABLE_COLUMN_RENAME + "\n" +
					"DESCRIPTION;COMMENTARY");

		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("alter table SCOTT . EMP add column AMOUNT number(5,2) default 0"),
				OraSqlUtils.ALTER_TABLE_COLUMN_ADD + "\n" +
					"AMOUNT number(5|2) default 0");
		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("alter table SCOTT.DEPT add DESCRIPTION varchar2(255)"),
				OraSqlUtils.ALTER_TABLE_COLUMN_ADD + "\n" +
					"DESCRIPTION varchar2(255)");
		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("ALTER TABLE SCOTT . EMP ADD (jcol JSON, AMOUNT number(5,2) default -1)"),
				OraSqlUtils.ALTER_TABLE_COLUMN_ADD + "\n" +
					"jcol JSON;AMOUNT number(5|2) default -1");

		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("alter table SCOTT . EMP modify REF_NO number(9) default 0"),
				OraSqlUtils.ALTER_TABLE_COLUMN_MODIFY + "\n" +
					"REF_NO number(9) default 0");
		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("alter table SCOTT.DEPT modify column DESCRIPTION varchar2(1000)"),
				OraSqlUtils.ALTER_TABLE_COLUMN_MODIFY + "\n" +
					"DESCRIPTION varchar2(1000)");
		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("ALTER TABLE SCOTT . EMP MODIFY (REF_NO number(9) default 0, AMOUNT number(5,2) default -1)"),
				OraSqlUtils.ALTER_TABLE_COLUMN_MODIFY + "\n" +
					"REF_NO number(9) default 0;AMOUNT number(5|2) default -1");

		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("alter table SALARY drop column SALGRADE"),
				OraSqlUtils.ALTER_TABLE_COLUMN_DROP + "\n" +
					"SALGRADE");
		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("alter table SCOTT.SALARY drop (SALGRADE, BONUS)"),
				OraSqlUtils.ALTER_TABLE_COLUMN_DROP + "\n" +
					"SALGRADE;BONUS");
		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("alter table SCOTT. EMP set unused (SALGRADE)"),
				OraSqlUtils.ALTER_TABLE_COLUMN_DROP + "\n" +
					"SALGRADE");
		assertEquals("Unexpected results",
				OraSqlUtils.alterTablePreProcessor("alter table SCOTT .EMP set unused (BONUS, SALGRADE)"),
				OraSqlUtils.ALTER_TABLE_COLUMN_DROP + "\n" +
					"BONUS;SALGRADE");
	}
}
