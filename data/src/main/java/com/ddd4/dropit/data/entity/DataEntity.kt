package com.ddd4.dropit.data.entity

import androidx.room.*
import java.util.*

sealed class DataEntity {

    /**
     * Room Database Entity
     * "folder" table
     * @param id                id
     * @param thumbnail         folder thumbnail image path
     * @param createAt          create date
     * @param updateAt          update date
     */
    @Entity(tableName = "folder")
    data class Folder (
        @PrimaryKey(autoGenerate = true) var id: Long,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "thumbnail") var thumbnail: String,
        @ColumnInfo(name = "create_at") var createAt: Date,
        @ColumnInfo(name = "update_at") var updateAt: Date? = Date()
    ): DataEntity()

    /**
     * Room Database Entity
     * "item" table
     * @param id                id
     * @param folderId          relate folder id
     * @param categoryId        relate category id
     * @param image             item image path
     * @param startAt           start date of item
     * @param endAt             end date of shelf-life
     * @param createAt          create date
     * @param updateAt          update date
     */
    @Entity(
        tableName = "item",
        foreignKeys = [ForeignKey(
            entity = Folder::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("folder_id"),
            onDelete = ForeignKey.CASCADE
        )])
    data class Item (
        @PrimaryKey(autoGenerate = true) var id: Long,
        @ColumnInfo(name = "folder_id") var folderId: Long? = 0,
        @ColumnInfo(name = "category_id") var categoryId: Long,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "image") var image: String,
        @ColumnInfo(name = "start_at") var startAt: Date,
        @ColumnInfo(name = "end_at") var endAt: Date,
        @ColumnInfo(name = "create_at") var createAt: Date,
        @ColumnInfo(name = "update_at") var updateAt: Date? = Date()
    ): DataEntity()

    data class FolderAndItems (
        @Embedded val folder: Folder,
        @Relation(parentColumn = "id", entityColumn = "folder_id")
        var items: List<Item> = ArrayList()
    ): DataEntity()
}