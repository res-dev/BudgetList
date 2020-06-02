package com.resdev.budgetlist.persistence.budget_item

@Entity(tableName = "todo")
data class BudgetItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "record_id", index = true)
    val id: Int,
    @ColumnInfo(name = "title", index = false)
    val title: String,
    @ColumnInfo(name = "description", index = false)
    val description: String,
    @ColumnInfo(name = "category_id", index = true)
    val categoryId: Int,
    @ColumnInfo(name = "is_complete", index = true)
    val isComplete: Boolean,
    @ColumnInfo(name = "created_at", index = false)
    val createdAt: Long,
    @ColumnInfo(name = "updated_at", index = false)
    val updatedAt: Long

) {
    fun toBudgetItem(): BudgetItem {
        return BudgetItem(id, title, description, categoryId, isComplete, createdAt, updatedAt)
    }

    companion object {
        fun from(source: BudgetItem): BudgetItemEntity {
            return BudgetItemEntity(
                source.id,
                source.title,
                source.description,
                source.categoryId,
                source.isComplete,
                source.createdAt,
                source.updatedAt
            )
        }
    }
}