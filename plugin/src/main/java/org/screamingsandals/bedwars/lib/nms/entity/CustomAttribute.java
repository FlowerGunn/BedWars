/*
 * Copyright (C) 2022 ScreamingSandals
 *
 * This file is part of Screaming BedWars.
 *
 * Screaming BedWars is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Screaming BedWars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Screaming BedWars. If not, see <https://www.gnu.org/licenses/>.
 */

package org.screamingsandals.bedwars.lib.nms.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.screamingsandals.bedwars.lib.nms.accessors.GenericAttributesAccessor;
import org.screamingsandals.bedwars.lib.nms.utils.ClassStorage;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Getter
public class CustomAttribute {
	public static final CustomAttribute MAX_HEALTH = new CustomAttribute(() -> ClassStorage.getField(GenericAttributesAccessor.getFieldMAX_HEALTH()));
	public static final CustomAttribute FOLLOW_RANGE = new CustomAttribute(() -> ClassStorage.getField(GenericAttributesAccessor.getFieldFOLLOW_RANGE()));
	public static final CustomAttribute KNOCKBACK_RESISTANCE = new CustomAttribute(() -> ClassStorage.getField(GenericAttributesAccessor.getFieldKNOCKBACK_RESISTANCE()));
	public static final CustomAttribute MOVEMENT_SPEED = new CustomAttribute(() -> ClassStorage.getField(GenericAttributesAccessor.getFieldMOVEMENT_SPEED()));
	public static final CustomAttribute FLYING_SPEED = new CustomAttribute(() -> ClassStorage.getField(GenericAttributesAccessor.getFieldFLYING_SPEED()));
	public static final CustomAttribute ATTACK_DAMAGE = new CustomAttribute(() -> ClassStorage.getField(GenericAttributesAccessor.getFieldATTACK_DAMAGE()));
	public static final CustomAttribute ATTACK_KNOCKBACK = new CustomAttribute(() -> ClassStorage.getField(GenericAttributesAccessor.getFieldATTACK_KNOCKBACK()));
	public static final CustomAttribute ATTACK_SPEED = new CustomAttribute(() -> ClassStorage.getField(GenericAttributesAccessor.getFieldATTACK_SPEED()));
	public static final CustomAttribute ARMOR = new CustomAttribute(() -> ClassStorage.getField(GenericAttributesAccessor.getFieldARMOR()));
	public static final CustomAttribute ARMOR_TOUGHNESS = new CustomAttribute(() -> ClassStorage.getField(GenericAttributesAccessor.getFieldARMOR_TOUGHNESS()));
	public static final CustomAttribute LUCK = new CustomAttribute(() -> ClassStorage.getField(GenericAttributesAccessor.getFieldLUCK()));

	private final Supplier<Object> object;
}
